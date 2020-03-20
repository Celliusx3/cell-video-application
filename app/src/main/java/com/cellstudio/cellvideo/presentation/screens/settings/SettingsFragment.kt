package com.cellstudio.cellvideo.presentation.screens.settings

import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.viewmodel.details.DetailsViewModel
import com.cellstudio.cellvideo.player.VideoPlayerFragment
import com.cellstudio.cellvideo.presentation.adapters.DetailsAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment

class SettingsFragment : BaseInjectorFragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var adapter: DetailsAdapter

    private lateinit var videoFragment: VideoPlayerFragment

    override fun getLayoutResource(): Int {
        return R.layout.fragment_details
    }


    companion object {
        val TAG = SettingsFragment::class.java.simpleName

        fun newInstance(): SettingsFragment {
            val bundle = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindView(view: View) {
        super.onBindView(view)


    }
}