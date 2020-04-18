package com.cellstudio.cellvideo.player.cellplayer.playercontrol

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.cellstudio.cellvideo.R

class DefaultCellLivePlayerControl : CellPlayerControl {
    private lateinit var context: Context
    private lateinit var orientation: Orientation
    private lateinit var controlsView: View

    // UI Elements of Main controls
    private lateinit var backButton: ImageView
    private lateinit var playPauseButton: ImageView
    private lateinit var volumeButton: ImageView
    private lateinit var centerMainContainerLayout: LinearLayout
    private lateinit var bottomMainContainerLayout: LinearLayout
    private lateinit var moreButton: ImageView
    private lateinit var tvPlayerControlsTitle: TextView


    // Drawables
    private val volumeOnIcon = R.drawable.ic_volume_white_24dp
    private val volumeOffIcon = R.drawable.ic_volume_off_white_24dp
    private val pauseIcon = R.drawable.ic_video_player_pause_white_24dp
    private val playIcon = R.drawable.ic_video_player_play_white_24dp

    // State of the controls and views
    private var isPaused: Boolean = false
    private var isInitialized = false
    private var isMuted = false

    // Determines if the player controls are visible or not
    private var isVisible = true
    // Determines if the controls are being animated in the hide/show transition
    private var isAnimating = false
    // Only used this for maintaining the paused/play state before and after skipping
    private var isPausedSkip = true

    // Listener for handling User interaction with controls
    private var playerListener: CellPlayerControlListener? = null

    // Handler for UI show/hide
    private var mHandler: Handler? = null

    override fun initialize(context: Context, orientation: Orientation) {
        this.context = context
        this.orientation = orientation
        mHandler = AnimationHandler(Looper.getMainLooper())
        val inflater = LayoutInflater.from(context)
        controlsView = inflater.inflate(R.layout.live_player_control, null)
        findViews()
        setInitialValues()
        isInitialized = true
    }

    override fun isInitialized(): Boolean {
        return isInitialized
    }

    override fun getView(): View {
        return controlsView
    }

    override fun registerCallbackListener(listener: CellPlayerControlListener): Boolean {
        this.playerListener = listener
        return true
    }

    override fun unregisterCallbackListener(listener: CellPlayerControlListener): Boolean {
        this.playerListener = null
        return true
    }

    override fun updatePosition(position_ms: Long) {}

    override fun updateDuration(duration_ms: Long) {}

    override fun setPlayPause(isPlaying: Boolean) {
        isPaused = !isPlaying
        updatePlayPauseIcon()
    }

    override fun isPlaying(): Boolean {
        return !isPaused
    }

    override fun updateFullScreenIcon(isFullScreen: Boolean) {}

    override fun setTitle(title: String) {
        tvPlayerControlsTitle.text = title
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // Start animations
        if (!isAnimating && !isVisible && event?.action == MotionEvent.ACTION_DOWN) {
            showControls()
        } else if (event?.action == MotionEvent.ACTION_DOWN && !isAnimating && isVisible) {
            // If the user press again the screen and the controls are visible, then hide them
            mHandler?.removeCallbacks(hideControlsRunnable)
            hideControls()
        }
        return false
    }

    private fun findViews() {
        // Main controls layout
        centerMainContainerLayout =  controlsView.findViewById<View>(R.id.llPlayerControlsCenter) as LinearLayout
        bottomMainContainerLayout = controlsView.findViewById<View>(R.id.llPlayerControlsBtmBar) as LinearLayout
        playPauseButton = controlsView.findViewById<View>(R.id.ivPlayerControlsPlayPause) as ImageView
        playPauseButton.setOnClickListener(onClickListener)
        volumeButton = controlsView.findViewById<View>(R.id.ivPlayerControlsVolume) as ImageView
        volumeButton.setOnClickListener(onClickListener)
        backButton = controlsView.findViewById<View>(R.id.ivPlayerControlsBack) as ImageView
        backButton.setOnClickListener(onClickListener)
        moreButton = controlsView.findViewById<View>(R.id.ivPlayerControlsMore) as ImageView
        moreButton.setOnClickListener(onClickListener)
        tvPlayerControlsTitle = controlsView.findViewById<View>(R.id.tvPlayerControlsTitle) as TextView
    }

    private fun setInitialValues() {
        refreshViews()
        initializeAnimations()
    }

    /**
     * Initialize the player controls as hidden, then show them
     */
    private fun initializeAnimations() {
        isAnimating = false
        isVisible = false
        controlsView.visibility = View.VISIBLE
        controlsView.alpha = 0.0f
        // Force layout re draw
        controlsView.requestLayout()
        controlsView.postInvalidate()

        showControls()
    }

    /**
     * Main onClick listener for all clickable views
     */
    private val onClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.ivPlayerControlsPlayPause -> {
                togglePlayPauseIcon()
                requestPlayPause(!isPaused)
                restartHidingTimer()
            }
            R.id.ivPlayerControlsVolume -> {
                toggleVolumeButton()
                requestVolume()
                restartHidingTimer()
            }
            R.id.ivPlayerControlsBack -> {
                requestBack()
                restartHidingTimer()
            }
        }
    }

    private fun requestVolume() {
        playerListener?.onMuteChanged(isMuted)
    }

    private fun requestBack() {
        playerListener?.onBackPressed()
    }

    private fun requestPlayPause(play: Boolean) {
        if (playerListener != null) {
            if (play) {
                playerListener!!.onPlay()
            } else {
                playerListener!!.onPause()
            }
            setPlayPause(play)
        }
    }

    private fun toggleVolumeButton() {
        isMuted = !isMuted
        updateVolumeButton()
    }

    private fun updateVolumeButton() {
        mHandler?.post {
            if (isMuted) {
                volumeButton.setImageResource(volumeOffIcon)
            } else {
                volumeButton.setImageResource(volumeOnIcon)
            }
        }
    }

    private fun togglePlayPauseIcon() {
        isPaused = !isPaused
        updatePlayPauseIcon()
    }

    private fun updatePlayPauseIcon() {
        mHandler?.post {
            if (isPaused) {
                playPauseButton.setImageResource(playIcon)
            } else {
                playPauseButton.setImageResource(pauseIcon)
            }
        }
    }

    /**
     * Restart the timer used to auto-hide the controls. This is useful when some buttons are
     * clicked by the user.
     */
    private fun restartHidingTimer() {
        if (isVisible) {
            mHandler?.removeCallbacks(hideControlsRunnable)
            mHandler?.postDelayed(hideControlsRunnable, HIDE_ANIMATION_AFTER_MS)
        }
    }

    private fun showControls() {
        if (isAnimating) {
            return
        }
        mHandler?.post(showControlsRunnable)
    }

    private fun hideControls() {
        if (isAnimating) {
            return
        }
        mHandler?.post(hideControlsRunnable)
    }

    private val showControlsRunnable = Runnable {
        if (isAnimating) {
            // Don't animate if another animation is running.
            return@Runnable
        }
        controlsView.animate().alpha(1.0f).setDuration(ANIMATION_SHOW_DURATION_MS).setListener(showAnimatorListener)
    }

    private val showAnimatorListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            isAnimating = true
            controlsView.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animator) {
            isAnimating = false
            isVisible = true
            mHandler?.postDelayed(hideControlsRunnable, HIDE_ANIMATION_AFTER_MS)
            // Make sure the controls are shown in the screen
            controlsView.requestLayout()
        }

        override fun onAnimationCancel(animation: Animator) {
            isAnimating = false
        }

        override fun onAnimationRepeat(animation: Animator) {}
    }

    private val hideControlsRunnable = Runnable {
        if (isAnimating) {
            // Don´t animate if another animation is running.
            return@Runnable
        }
        controlsView.animate().alpha(0.0f).setDuration(ANIMATION_HIDE_DURATION_MS).setListener(hideAnimatorListener)
    }

    private val hideAnimatorListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            // Make sure the controls are shown in the screen
            controlsView.requestLayout()
            isAnimating = true
        }

        override fun onAnimationEnd(animation: Animator) {
            isAnimating = false
            isVisible = false
            controlsView.visibility = View.GONE
        }

        override fun onAnimationCancel(animation: Animator) {
            isAnimating = false
        }

        override fun onAnimationRepeat(animation: Animator) {}
    }

    /**
     * This method takes the state values and refresh the views according to them
     */
    private fun refreshViews() {
        updatePlayPauseIcon()
    }

    companion object {
        private val TAG = DefaultCellVideoPlayerControl::class.java.simpleName
        // Duration of show animation
        private const val ANIMATION_SHOW_DURATION_MS: Long = 100
        // Duration of hide animation
        private const val ANIMATION_HIDE_DURATION_MS: Long = 500
        // Time after the controls are automatically hidden
        private const val HIDE_ANIMATION_AFTER_MS: Long = 5000
        // Seekbar max position

        /**
         * Handler class for managing messages to show/hide the controls
         */
        private class AnimationHandler(looper: Looper) : Handler(looper)
    }
}