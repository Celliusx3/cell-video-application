package com.cellstudio.cellvideo.player.cellplayer

import android.content.Context
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerPlaySpeed
import com.cellstudio.cellvideo.player.cellplayer.models.QualityLevel
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ui.PlayerView

interface CellPlayer {
    fun init(context: Context, playerView: PlayerView)

    fun play(url: String)
    fun play()
    fun seekTo(position: Long)
    fun seekBy(position: Long)
    fun pause()
    fun stop()

    fun setMute(isMute: Boolean)
    fun setPlaybackSpeed(speed: CellPlayerPlaySpeed)

    fun addPlayerListener(listener: CellPlayerListener)

    fun getCellPlayerLifecycle(): CellPlayerLifecycle
    fun getCellPlayerData(): CellPlayerData

    interface CellPlayerListener {
        fun onBufferListener()
        fun onErrorListener(throwable: ExoPlaybackException)
        fun onTimeListener(position: Long, duration: Long)
        fun onPlayListener()
        fun onPauseListener()
        fun onCompleteListener()
        fun onIdleListener()
        fun onReadyListener()
        fun onLoadingListener(isLoading: Boolean)
    }

    interface CellPlayerLifecycle {
        fun onResume()
        fun onPause()
        fun onStop()
        fun onDestroy()
    }

    interface CellPlayerData {
        fun getQualityLevels(): List<QualityLevel>
    }
}