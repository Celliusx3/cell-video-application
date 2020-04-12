package com.cellstudio.cellvideo.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.player.cellplayer.CellPlayer
import com.cellstudio.cellvideo.player.cellplayer.DefaultCellPlayer
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerAction
import com.cellstudio.cellvideo.player.cellplayer.models.CellPlayerPlaySpeed
import com.cellstudio.cellvideo.player.cellplayer.playercontrol.CellPlayerControl
import com.cellstudio.cellvideo.player.cellplayer.playercontrol.CellPlayerControlListener
import com.cellstudio.cellvideo.player.cellplayer.playercontrol.DefaultCellVideoPlayerControl
import com.cellstudio.cellvideo.player.cellplayer.playercontrol.Orientation
import com.cellstudio.cellvideo.player.cellplayer.utils.UIVisibilityUtil
import com.cellstudio.cellvideo.presentation.base.BaseFragment
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.source.BehindLiveWindowException
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_video_player.*

class VideoPlayerFragment : BaseFragment() {
    private lateinit var url: String
    private lateinit var title: String
    private lateinit var playerControl: CellPlayerControl
    private lateinit var videoPlayer: CellPlayer

    private var isFullScreen = false
    private val pressFullScreenButtonSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val pressPlayButtonSubject: PublishSubject<Unit> = PublishSubject.create()
    private val pressPauseButtonSubject: PublishSubject<Unit> = PublishSubject.create()

    var listener: Listener? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_video_player
    }

    companion object {
        val TAG = VideoPlayerFragment::class.java.simpleName

        // Number of seconds to seek fw bw when FF or RW is pressed.
        private const val SEEK_M_SECONDS: Long = 10000

        fun newInstance(): VideoPlayerFragment {
            val bundle = Bundle()
            val fragment = VideoPlayerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    fun setUrl(url: String) {
        this.url = url
        videoPlayer.play(url)
    }

    fun setTitle(title: String) {
        this.title = title
        playerControl.setTitle(title)
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        createVideoPlayer()
        initializeVideoPlayerListener()
        initializeVideoPlayerControls()
        pvVideoPlayerPlayer.setOnTouchListener(playerControl)
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.getCellPlayerLifecycle().onResume()
        UIVisibilityUtil.enableKeepScreenOn(activity as AppCompatActivity?)

        if (isFullScreen) {
            UIVisibilityUtil.enableWindowFullScreen(activity = activity as AppCompatActivity?)
        } else {
            UIVisibilityUtil.disableWindowFullScreen(activity = activity as AppCompatActivity?)
        }
    }

    override fun onStop() {
        super.onStop()
        videoPlayer.getCellPlayerLifecycle().onStop()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.getCellPlayerLifecycle().onPause()
        UIVisibilityUtil.disableKeepScreenOn(activity as AppCompatActivity?)
        UIVisibilityUtil.disableWindowFullScreen(activity = activity as AppCompatActivity?)
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayer.getCellPlayerLifecycle().onDestroy()
    }

    private fun initializeVideoPlayerListener() {
        videoPlayer.addPlayerListener(object: CellPlayer.CellPlayerListener{
            override fun onBufferListener() { Log.d(TAG, "onBuffer")}
            override fun onErrorListener(throwable: ExoPlaybackException) { onPlayerError(throwable) }
            override fun onTimeListener(position: Long, duration: Long) {
                Log.d(TAG, "onTime: $position | $duration")
                playerControl.updateDuration(duration)
                playerControl.updatePosition(position)
            }
            override fun onPlayListener() {playerControl.setPlayPause(true)}
            override fun onPauseListener() {playerControl.setPlayPause(false)}
            override fun onCompleteListener() { Log.d(TAG, "onCompleteListener") }
            override fun onIdleListener() {Log.d(TAG, "onIdleListener")}
            override fun onReadyListener() { Log.d(TAG, "onReadyListener") }
        })
    }

    override fun onBindData(view: View?) {
        super.onBindData(view)
        disposable.add(pressPlayButtonSubject.subscribe { videoPlayer.play() })
        disposable.add(pressPauseButtonSubject.subscribe { videoPlayer.pause() })
        disposable.add(pressFullScreenButtonSubject.subscribe {isNextFullScreen ->
            if (isNextFullScreen) {
                enableFullScreen()
            } else {
                disableFullScreen()
            }
            isFullScreen = isNextFullScreen
        })

        listener?.onFragmentReady()
    }


    private fun createVideoPlayer() {
        if (context == null) { return }

        videoPlayer = DefaultCellPlayer()
        videoPlayer.init(context!!, pvVideoPlayerPlayer)

    }

    private fun initializeVideoPlayerControls () {
        playerControl = DefaultCellVideoPlayerControl()

        if (!playerControl.isInitialized())
            playerControl.initialize(context!!, Orientation.LANDSCAPE)

        // Add the player view in front of the player
        val viewControl = playerControl.getView()
        if (viewControl.parent != null) {
            (viewControl.parent as ViewGroup).removeView(viewControl)
        }
        flVideoPlayerControls.addView(viewControl)
        // Register callbacks
        playerControl.registerCallbackListener(controlsListener)
        playerControl.updateFullScreenIcon(false)
    }

    /**
     * Callbacks from the player controls
     */
    private val controlsListener = object : CellPlayerControlListener {
        override fun onBackPressed() { pressFullScreenButtonSubject.onNext(false) }
        override fun onPlay() { pressPlayButtonSubject.onNext(Unit) }
        override fun onPause() { pressPauseButtonSubject.onNext(Unit) }
        override fun onSeekForward() { onSeekingTouchNSec(true) }
        override fun onSeekBackward() { onSeekingTouchNSec(false) }
        override fun onSeekTo(position: Long) { onSeekToPosition(position) }
        override fun onSeekComplete() {}
        override fun onMuteChanged(isMuted: Boolean) { mutePlayer(isMuted) }
        override fun onFullScreenIconPressed(isFullScreen: Boolean) { pressFullScreenButtonSubject.onNext(isFullScreen) }
        override fun onMorePressed() {
            val fragment = VideoPlayerMoreFragment.newInstance()
            fragment.listener = object: VideoPlayerMoreFragment.Listener {
                override fun onHide() {
                    if (isFullScreen) {
                        enableFullScreen()
                    } else {
                        disableFullScreen()
                    }
                }
                override fun onFragmentReady() { fragment.setActions(listOf(getSpeedAction(), getQualityAction())) }
            }
            fragment.show(childFragmentManager, null)

        }
    }

    // Seek to new position when Seek button is pressed
    private fun onSeekingTouchNSec(bIsForward: Boolean) {
        if (bIsForward) {
            videoPlayer.seekBy(SEEK_M_SECONDS)
        } else {
            videoPlayer.seekBy(-SEEK_M_SECONDS)
        }
    }

    private fun onSeekToPosition(lNewPosition: Long) {
        videoPlayer.seekTo(lNewPosition)
    }

    private fun mutePlayer(isMuted: Boolean) {
        videoPlayer.setMute(isMuted)
    }

    /**
     * This method should  be called when the player is requested to be fullscreen, so the dimensions
     * should change to match_parent, match_parent
     */
    @SuppressLint("SourceLockedOrientationActivity")
    fun enableFullScreen() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        if (activity is AppCompatActivity) {
            UIVisibilityUtil.enableWindowFullScreen(activity = activity as AppCompatActivity?)
        }

        listener?.onFullScreenEnabled()
    }

    /**
     * This method should  be called when the player is requested to return from fullscreen, so the dimensions
     * should change to the default ones, i.e. the player will be embedded
     */
    @SuppressLint("SourceLockedOrientationActivity")
    fun disableFullScreen() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        if (activity is AppCompatActivity) {
            UIVisibilityUtil.disableWindowFullScreen(activity = activity as AppCompatActivity?)
        }

        listener?.onFullScreenDisabled()
    }

    private fun getSpeedAction(): CellPlayerAction{
        return CellPlayerAction(context!!.getString(R.string.playback_speed), true, R.drawable.ic_video_player_playback_speed_white_24dp,
            View.OnClickListener { getVideoPlayerSpeedFragment().show(childFragmentManager, null) })
    }

    private fun getQualityAction(): CellPlayerAction{
        return CellPlayerAction(context!!.getString(R.string.quality), true, R.drawable.ic_video_player_quality_white_24dp,
            View.OnClickListener { getVideoPlayerSpeedFragment().show(childFragmentManager, null) })
    }

    private fun getVideoPlayerSpeedFragment(): VideoPlayerSpeedFragment {
        val speedFragment = VideoPlayerSpeedFragment.newInstance(CellPlayerPlaySpeed.NORMAL)
        speedFragment.listener = object : VideoPlayerSpeedFragment.Listener {
            override fun onHide() {
                onHideBottomSheetFragment()
            }

            override fun onSpeedSelected(speed: CellPlayerPlaySpeed) {
                videoPlayer.setPlaybackSpeed(speed)
            }
        }

        return speedFragment
    }

    private fun onHideBottomSheetFragment() {
        if (isFullScreen) {
            enableFullScreen()
        } else {
            disableFullScreen()
        }
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

    private fun onPlayerError(error: ExoPlaybackException) {
        if (isBehindLiveWindow(error)) {
            videoPlayer.init(context!!, pvVideoPlayerPlayer)
            videoPlayer.play(url)
        } else {
            tvVideoPlayerError.visibility = View.VISIBLE
            flVideoPlayerControls.visibility = View.GONE
        }

    }




    interface Listener {
        fun onFragmentReady()
        fun onFullScreenEnabled()
        fun onFullScreenDisabled()
    }

}