package com.cellstudio.cellvideo.presentation.screens.details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.details.DetailsViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.details.DetailsViewModelImpl
import com.cellstudio.cellvideo.player.VideoPlayerFragment
import com.cellstudio.cellvideo.presentation.adapters.DetailsAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseInjectorFragment() {
    private var model: VideoPresentationModel? = null

    private lateinit var viewModel: DetailsViewModel
    private lateinit var adapter: DetailsAdapter

    private lateinit var videoFragment: VideoPlayerFragment

    override fun getLayoutResource(): Int {
        return R.layout.fragment_details
    }


    companion object {
        const val EXTRA_DETAILS = "EXTRA_DETAILS"
        val TAG = DetailsFragment::class.java.simpleName

        fun newInstance(model: VideoPresentationModel): DetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_DETAILS, model)

            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onGetInputData() {
        super.onGetInputData()
        arguments.let {
            model = it?.getParcelable(EXTRA_DETAILS)
        }
    }

    private fun setupAdapter(models: List<Pair<String, String>>, selectedPosition: Int) {
        val layoutManager = GridLayoutManager(context!!, 4)
        adapter = DetailsAdapter(models, context!!, selectedPosition)
        adapter.setListener(object: DetailsAdapter.Listener {
            override fun onModelClicked(title: String, url: String) {
                videoFragment.setTitle(title)
                videoFragment.setUrl(url)
            }
        })
        rvDetailsEpisodes.layoutManager = layoutManager
        rvDetailsEpisodes.adapter = adapter

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

            override fun onFragmentReady() {}

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

        viewModel = ViewModelProvider(this,viewModelFactory).get(DetailsViewModelImpl::class.java)

        viewModel.setInput(object: DetailsViewModel.Input {
            override val id: Int = Integer.parseInt(model?.id ?: "")
        })
        viewModel.getViewEvent().startScreen()
        viewModel.getViewData().getDetails().observe(this, Observer {
            val test = it.videoUrl?.toList()?: listOf()
            setupAdapter(test, 0)
            videoFragment.setTitle(test[0].first)
            videoFragment.setUrl(test[0].second)
        })
    }
}