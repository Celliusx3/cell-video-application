package com.cellstudio.cellvideo.player

//abstract class BasePlayerFragment : BaseFragment() {
//    private lateinit var url: String
//    private lateinit var playerControl: CellPlayerControl
//    private lateinit var videoPlayer: CellPlayer
//
//    private val pressPlayButtonSubject: PublishSubject<Unit> = PublishSubject.create()
//    private val pressPauseButtonSubject: PublishSubject<Unit> = PublishSubject.create()
//
//    var listener: Listener? = null
//
//    override fun getLayoutResource(): Int {
//        return R.layout.fragment_video_player
//    }
//
//    companion object {
//        val TAG = BasePlayerFragment::class.java.simpleName
//
//        // Number of seconds to seek fw bw when FF or RW is pressed.
//        private const val SEEK_M_SECONDS: Long = 10000
//    }
//
//
//
//    fun setUrl(url: String) {
//        this.url = url
//        videoPlayer.play(url)
//    }
//
//    override fun onBindView(view: View) {
//        super.onBindView(view)
//        createVideoPlayer()
//        initializeVideoPlayerListener()
//        initializeVideoPlayerControls()
//        pvVideoPlayerPlayer.setOnTouchListener(playerControl)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        videoPlayer.getCellPlayerLifecycle().onResume()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        videoPlayer.getCellPlayerLifecycle().onStop()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        videoPlayer.getCellPlayerLifecycle().onPause()
//        UIVisibilityUtil.disableWindowFullScreen(activity = activity as AppCompatActivity?)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        videoPlayer.getCellPlayerLifecycle().onDestroy()
//    }
//
//    private fun initializeVideoPlayerListener() {
//        videoPlayer.addPlayerListener(object: CellPlayer.CellPlayerListener{
//            override fun onBufferListener() {}
//            override fun onErrorListener(throwable: ExoPlaybackException) {}
//            override fun onTimeListener(position: Long, duration: Long) {
//                Log.d(TAG, "onTime: $position | $duration")
//                playerControl.updateDuration(duration)
//                playerControl.updatePosition(position)
//            }
//            override fun onPlayListener() {playerControl.setPlayPause(true)}
//            override fun onPauseListener() {playerControl.setPlayPause(false)}
//            override fun onCompleteListener() { Log.d(TAG, "onCompleteListener") }
//            override fun onIdleListener() { Log.d(TAG, "onIdleListener")}
//            override fun onReadyListener() { Log.d(TAG, "onReadyListener") }
//        })
//    }
//
//    override fun onBindData(view: View?) {
//        super.onBindData(view)
//        disposable.add(pressPlayButtonSubject.subscribe { videoPlayer.play() })
//        disposable.add(pressPauseButtonSubject.subscribe { videoPlayer.pause() })
//
//        listener?.onFragmentReady()
//    }
//
//
//    private fun createVideoPlayer() {
//        if (context == null) { return }
//
//        videoPlayer = DefaultCellPlayer()
//        videoPlayer.init(context!!, pvVideoPlayerPlayer)
//
//    }
//
//    private fun initializeVideoPlayerControls () {
//        playerControl = DefaultCellLivePlayerControl()
//
//        if (!playerControl.isInitialized())
//            playerControl.initialize(context!!, Orientation.LANDSCAPE)
//
//        // Add the player view in front of the player
//        val viewControl = playerControl.getView()
//        if (viewControl.parent != null) {
//            (viewControl.parent as ViewGroup).removeView(viewControl)
//        }
//        flVideoPlayerContainer.addView(viewControl)
//        // Register callbacks
//        playerControl.registerCallbackListener(controlsListener)
//        playerControl.updateFullScreenIcon(false)
//    }
//
//    /**
//     * Callbacks from the player controls
//     */
//    private val controlsListener = object : CellPlayerControlListener {
//        override fun onBackPressed() { pressFullScreenButtonSubject.onNext(false) }
//        override fun onPlay() { pressPlayButtonSubject.onNext(Unit) }
//        override fun onPause() { pressPauseButtonSubject.onNext(Unit) }
//        override fun onSeekForward() { onSeekingTouchNSec(true) }
//        override fun onSeekBackward() { onSeekingTouchNSec(false) }
//        override fun onSeekTo(position: Long) { onSeekToPosition(position) }
//        override fun onSeekComplete() {}
//        override fun onMuteChanged(isMuted: Boolean) { mutePlayer(isMuted) }
//        override fun onFullScreenIconPressed(isFullScreen: Boolean) { pressFullScreenButtonSubject.onNext(isFullScreen) }
//        override fun onMorePressed() {
//            val fragment = VideoPlayerSpeedFragment.newInstance(CellPlayerPlaySpeed.NORMAL)
//            fragment.listener = object: VideoPlayerSpeedFragment.Listener {
//                override fun onSpeedSelected(speed: CellPlayerPlaySpeed) { videoPlayer.setPlaybackSpeed(speed) }
//
//                override fun onHide() {
//                    if (isFullScreen) {
//                        enableFullScreen()
//                    } else {
//                        disableFullScreen()
//                    }
//                }
//            }
//            fragment.show(childFragmentManager, null)
//        }
//    }
//
//    // Seek to new position when Seek button is pressed
//    private fun onSeekingTouchNSec(bIsForward: Boolean) {
//        if (bIsForward) {
//            videoPlayer.seekBy(SEEK_M_SECONDS)
//        } else {
//            videoPlayer.seekBy(-SEEK_M_SECONDS)
//        }
//    }
//
//    private fun onSeekToPosition(lNewPosition: Long) {
//        videoPlayer.seekTo(lNewPosition)
//    }
//
//    private fun mutePlayer(isMuted: Boolean) {
//        videoPlayer.setMute(isMuted)
//    }
//
//    /**
//     * This method should  be called when the player is requested to be fullscreen, so the dimensions
//     * should change to match_parent, match_parent
//     */
//    @SuppressLint("SourceLockedOrientationActivity")
//    fun enableFullScreen() {
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
//        if (activity is AppCompatActivity) {
//            UIVisibilityUtil.enableWindowFullScreen(activity = activity as AppCompatActivity?)
//        }
//
//        listener?.onFullScreenEnabled()
//    }
//
//    /**
//     * This method should  be called when the player is requested to return from fullscreen, so the dimensions
//     * should change to the default ones, i.e. the player will be embedded
//     */
//    @SuppressLint("SourceLockedOrientationActivity")
//    fun disableFullScreen() {
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
//        if (activity is AppCompatActivity) {
//            UIVisibilityUtil.disableWindowFullScreen(activity = activity as AppCompatActivity?)
//        }
//
//        listener?.onFullScreenDisabled()
//
//    }
//
//    interface Listener {
//        fun onFragmentReady()
//        fun onFullScreenEnabled()
//        fun onFullScreenDisabled()
//    }
//
//}