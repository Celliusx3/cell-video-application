package com.cellstudio.cellvideo.presentation.screens.livesource

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.player.VideoPlayerFragment
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import kotlinx.android.synthetic.main.fragment_details.*

class LiveSourceFragment : BaseInjectorFragment() {
    private var model: String? = null

    private lateinit var videoFragment: VideoPlayerFragment

    override fun getLayoutResource(): Int {
        return R.layout.fragment_details
    }


    companion object {
        const val EXTRA_LIVE_SOURCE = "EXTRA_LIVE_SOURCE"
        val TAG = LiveSourceFragment::class.java.simpleName

        fun newInstance(model: String): LiveSourceFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_LIVE_SOURCE, model)
            val fragment = LiveSourceFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onGetInputData() {
        super.onGetInputData()
        arguments.let {
            model = it?.getString(EXTRA_LIVE_SOURCE, "")
        }
    }

    private fun setupVideoScreen() {
        videoFragment = VideoPlayerFragment.newInstance()
        videoFragment.listener  = object: VideoPlayerFragment.Listener {
            override fun onFullScreenDisabled() {
                // Gets the layout params that will allow you to resize the layout
                val params = flVideoPlayerContainer.layoutParams ?: return
                // Changes the height and width to the specified *pixels*
                // Use the measured dimensions if are set
                val resources = flVideoPlayerContainer.context.resources
                params.height = resources.getDimension(R.dimen.embedded_video_player_height).toInt()
                flVideoPlayerContainer.layoutParams = params
                if (llDetailsDetails.parent == null)
                    llDetailsRoot.addView(llDetailsDetails)
            }

            override fun onFragmentReady() {
                videoFragment.setUrl(model?: "")
            }

            override fun onFullScreenEnabled() {
                // Gets the layout params that will allow you to resize the layout
                val params = flVideoPlayerContainer.layoutParams ?: return
                // Changes the height and width to fit the screen
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                flVideoPlayerContainer.layoutParams = params
                if (llDetailsDetails.parent != null)
                    llDetailsRoot.removeView(llDetailsDetails)
            }

        }
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flVideoPlayerContainer, videoFragment)
        fragmentTransaction.commit()
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupVideoScreen()
    }
}