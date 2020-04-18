package com.cellstudio.cellvideo.player.cellplayer

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerPlaySpeed
import com.cellstudio.cellvideo.player.cellplayer.models.QualityLevel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.ExoMediaCrypto
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class DefaultCellPlayer: CellPlayer {
    private var disTimer: Disposable? = null
    private var trackSelector: MappingTrackSelector? = null
    private val qualityLevels: MutableList<QualityLevel> = mutableListOf<QualityLevel>()

    private var player: SimpleExoPlayer ?= null
    private var dataSourceFactory: DataSource.Factory ?= null

    private val listeners = mutableListOf<CellPlayer.CellPlayerListener>()

    override fun init(context: Context, playerView: PlayerView) {
        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter.Builder(context).build()
        initExoPlayer(context, bandwidthMeter, playerView)
        dataSourceFactory = buildDataSourceFactory(context, bandwidthMeter)
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

    override fun getCellPlayerData(): CellPlayer.CellPlayerData {
        return object: CellPlayer.CellPlayerData{
            override fun getQualityLevels(): List<QualityLevel> {
                return qualityLevels
            }
        }
    }

    override fun play(url: String) {
        val uri = Uri.parse(url)
        val videoSource = createCleanDataSource(uri, "", DrmSessionManager.getDummyDrmSessionManager<ExoMediaCrypto>(), dataSourceFactory!!)
        player?.prepare(videoSource)
        player?.playWhenReady = true
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

    private fun initExoPlayer(context: Context, bandwidthMeter: BandwidthMeter, playerView: PlayerView) {

        val adaptiveTrackSelection: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory()
        trackSelector = DefaultTrackSelector(context, adaptiveTrackSelection)

        player = SimpleExoPlayer.Builder(context)
            .setBandwidthMeter(bandwidthMeter)
            .setTrackSelector(trackSelector!!)
            .build()

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

    private fun buildDataSourceFactory(context: Context, bandwidthMeter: BandwidthMeter): DataSource.Factory {
        return DefaultDataSourceFactory(context, buildHttpDataSourceFactory(context, bandwidthMeter, "Test"))
    }

    private fun buildHttpDataSourceFactory(context: Context, bandwidthMeter: BandwidthMeter, userAgent: String): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(userAgent,
            DefaultBandwidthMeter.Builder(context).build(),
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
           true)

    }

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
            listeners.forEach { it.onLoadingListener(isLoading) }
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    listeners.forEach { it.onBufferListener() }
                }
                Player.STATE_ENDED -> {
                    listeners.forEach { it.onCompleteListener() }
                }
                Player.STATE_IDLE -> {
                    listeners.forEach { it.onIdleListener() }
                }
                Player.STATE_READY -> {
                    listeners.forEach { it.onReadyListener() }
                }
            }
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            listeners.forEach { it.onErrorListener(error) }
        }

        override fun onTracksChanged(
            trackGroups: TrackGroupArray,
            trackSelections: TrackSelectionArray
        ) {
            val mappedTrackInfo = trackSelector?.currentMappedTrackInfo ?: return
            qualityLevels.clear()
            for (rendererIndex in 0 until mappedTrackInfo.rendererCount) {
                val rendererTrackGroups =
                    mappedTrackInfo.getTrackGroups(rendererIndex)
                val trackSelection = trackSelections[rendererIndex]
                if (rendererTrackGroups.length > 0) {
                    for (groupIndex in 0 until rendererTrackGroups.length) {
                        val trackGroup = rendererTrackGroups[groupIndex]
                        for (trackIndex in 0 until trackGroup.length) {
                            val format = trackGroup.getFormat(trackIndex)
                            if (format.sampleMimeType!!.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
                                if (qualityLevels.isEmpty()) {
                                    val qlAuto = QualityLevel(label = "Auto", groupIndex = groupIndex, rendererIndex = rendererIndex)
                                    qualityLevels.add(qlAuto)

                                    val qualityLevel = QualityLevel(
                                        width = format.width,
                                        height = format.height,
                                        bitrate = format.bitrate,
                                        label = format.id?: "",
                                        trackIndex = trackIndex,
                                        groupIndex = groupIndex,
                                        rendererIndex = rendererIndex
                                    )
                                    qualityLevels.add(qualityLevel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        val TAG = DefaultCellPlayer::class.java.simpleName
    }
}