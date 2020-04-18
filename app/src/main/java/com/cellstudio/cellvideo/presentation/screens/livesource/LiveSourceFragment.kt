package com.cellstudio.cellvideo.presentation.screens.livesource

import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.player.LiveSourcePlayerFragment
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment

class LiveSourceFragment : BaseInjectorFragment() {
    private lateinit var url: String
    private lateinit var title: String

    private lateinit var videoFragment: LiveSourcePlayerFragment

    override fun getLayoutResource(): Int {
        return R.layout.fragment_live_source
    }

    companion object {
        val TAG = LiveSourceFragment::class.java.simpleName

        private const val EXTRA_LIVE_SOURCE_URL = "EXTRA_LIVE_SOURCE_URL"
        private const val EXTRA_LIVE_SOURCE_TITLE = "EXTRA_LIVE_SOURCE_TITLE"

        fun newInstance(url: String, title: String): LiveSourceFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_LIVE_SOURCE_URL, url)
            bundle.putString(EXTRA_LIVE_SOURCE_TITLE, title)
            val fragment = LiveSourceFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onGetInputData() {
        super.onGetInputData()
        arguments.let {
            url = it?.getString(EXTRA_LIVE_SOURCE_URL) ?: ""
            title = it?.getString(EXTRA_LIVE_SOURCE_TITLE) ?: ""
        }
    }

    private fun setupVideoScreen() {
        videoFragment = LiveSourcePlayerFragment.newInstance()
        videoFragment.listener  = object: LiveSourcePlayerFragment.Listener {
            override fun onFragmentReady() {
                videoFragment.setUrl(url)
                videoFragment.setTitle(title)
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