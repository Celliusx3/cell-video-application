package com.cellstudio.cellvideo.player.cellplayer

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerPlaySpeed
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.ExoMediaCrypto
import com.google.android.exoplayer2.source.BehindLiveWindowException
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.util.Util
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class DefaultCellPlayer: CellPlayer {
    private var disTimer: Disposable? = null

    private val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
    private var downloadCache: Cache? = null


    private var player: SimpleExoPlayer ?= null
    private var dataSourceFactory: DataSource.Factory ?= null

    private val listeners = mutableListOf<CellPlayer.CellPlayerListener>()

    override fun init(context: Context, playerView: PlayerView) {
        initExoPlayer(context, playerView)
        dataSourceFactory = buildDataSourceFactory(context)
    }

    override fun addPlayerListener(listener: CellPlayer.CellPlayerListener) {
        this.listeners.add(listener)
    }

    override fun getCellPlayerLifecycle(): CellPlayer.CellPlayerLifecycle {
        return object: CellPlayer.CellPlayerLifecycle {
            override fun onDestroy() {
                disTimer?.dispose()
                stop()
            }

            override fun onPause() {
                disTimer?.dispose()
                pause()
            }

            override fun onResume() {
                startOnTimeTimer()
                play()
            }

            override fun onStop() {
                disTimer?.dispose()
                pause()
            }
        }

    }

    override fun play(url: String) {
        val uri = Uri.parse(url)
        val videoSource = createCleanDataSource(uri, "", DrmSessionManager.getDummyDrmSessionManager<ExoMediaCrypto>(), dataSourceFactory!!)
        player?.prepare(videoSource)
        player?.playWhenReady = true
//        val loading_ripple_scale_interpolator = createCleanDataSource(Uri)
//        player.prepare()
    }

    override fun play() {
        player?.playWhenReady = true
        listeners.forEach { it.onPlayListener() }
    }

    override fun pause() {
        player?.playWhenReady = false
        listeners.forEach { it.onPauseListener() }
    }

    override fun stop() {
        player?.stop()
    }

    override fun seekTo(position: Long) {
        player?.seekTo(position)
    }

    override fun seekBy(position: Long) {
        val realPostion = (player?.currentPosition?: 0) + position
        val tempPosition = when {
            realPostion <= 0 -> { 0 }
            realPostion > player?.duration?: 0 -> { player?.duration?: 0 }
            else -> { realPostion }
        }

        player?.seekTo(tempPosition)
    }

    private fun initExoPlayer(context: Context, playerView: PlayerView) {
        player = SimpleExoPlayer.Builder(context).build()
        playerView.player = player
        playerView.useController = false
        player?.addListener(playerEventListener)
    }

    private fun createCleanDataSource(uri: Uri,
                                      extension: String,
                                      drmSessionManager: DrmSessionManager<*>,
                                      dataSourceFactory: DataSource.Factory): MediaSource {
        return when (@C.ContentType val type = Util.inferContentType(uri, extension)) {
            C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            C.TYPE_OTHER -> ProgressiveMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }

    private fun buildDataSourceFactory(context: Context): DataSource.Factory {
        return DefaultDataSourceFactory(context, buildHttpDataSourceFactory("Test"))
//        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache())
    }

    private fun buildHttpDataSourceFactory(userAgent: String): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(userAgent,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
           true)

    }

    private fun isBehindLiveWindow(e: ExoPlaybackException): Boolean {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false
        }
        var cause: Throwable? = e.sourceException
        while (cause != null) {
            if (cause is BehindLiveWindowException) {
                return true
            }
            cause = cause.cause
        }
        return false
    }

//    @Synchronized
//    protected fun getDownloadCache(): Cache {
//        if (downloadCache == null) {
//            val downloadContentDirectory = File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY)
//            downloadCache =
//                SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), getDatabaseProvider())
//        }
//        return downloadCache
//    }


//    private fun buildReadOnlyCacheDataSource(upstreamFactory: DataSource.Factory, cache: Cache): CacheDataSourceFactory {
//        return CacheDataSourceFactory(cache,
//            upstreamFactory, FileDataSource.Factory(),
//        /* cacheWriteDataSinkFactory= */ null,
//        CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
//        /* eventListener= */ null)
//    }

//    private fun getDownloadDirectory(): File {
//        if (downloadDirectory == null) {
//            downloadDirectory = getExternalFilesDir(null)
//            if (downloadDirectory == null) {
//                downloadDirectory = getFilesDir()
//            }
//        }
//        return downloadDirectory
//    }

    override fun setMute(isMute: Boolean) {
        player?.volume = if (isMute) 0.0f else 1.0f
    }

    override fun setPlaybackSpeed(speed: CellPlayerPlaySpeed) {
        player?.setPlaybackParameters(PlaybackParameters(speed.speed))
    }

    private fun startOnTimeTimer() {
        disTimer?.dispose()
        disTimer = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                val position: Long = player?.currentPosition ?: 0
                val duration: Long = player?.duration ?: 0
                if (duration < 0) return@subscribe
                for (listener in listeners) {
                    listener.onTimeListener(position, duration)
                }
            }, {})
    }

    private val playerEventListener: Player.EventListener = object: Player.EventListener {
        override fun onLoadingChanged(isLoading: Boolean) {
            super.onLoadingChanged(isLoading)
            Log.d(TAG, "loading [$isLoading]")
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> { listeners.forEach { it.onBufferListener() } }
                Player.STATE_ENDED -> { listeners.forEach { it.onCompleteListener() }}
                Player.STATE_IDLE -> { listeners.forEach { it.onIdleListener() }}
                Player.STATE_READY -> { listeners.forEach { it.onReadyListener() }}
            }
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            listeners.forEach { it.onErrorListener(error) }
        }
    }

    companion object {
        val TAG = DefaultCellPlayer::class.java.simpleName
    }

}