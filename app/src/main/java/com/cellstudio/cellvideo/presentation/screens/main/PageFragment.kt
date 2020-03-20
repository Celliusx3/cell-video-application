package com.cellstudio.cellvideo.presentation.screens.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.main.MainViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.main.MainViewModelImpl
import com.cellstudio.cellvideo.presentation.adapters.VideoBannerAdapter
import com.cellstudio.cellvideo.presentation.adapters.VideoV2Adapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import com.cellstudio.cellvideo.presentation.components.StickyHeaderItemDecoration
import com.cellstudio.cellvideo.presentation.screens.details.DetailsActivity
import com.cellstudio.cellvideo.presentation.screens.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_page.*


class PageFragment: BaseInjectorFragment() {

    private lateinit var mainViewModel: MainViewModel
//    private lateinit var adapter: VideoAdapter
    private lateinit var bannerAdapter: VideoBannerAdapter
    private lateinit var pageAdapter: VideoV2Adapter
//    private lateinit var endlessScrollListener: EndlessRecyclerViewScrollListener


    companion object {
        fun newInstance(): PageFragment {
            val bundle = Bundle()

            val fragment = PageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return com.cellstudio.cellvideo.R.layout.fragment_page
    }

//    private fun setupBannerAdapter() {
////        val layoutManager = CenterZoomLinearLayoutManager(context!!)
//        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        bannerAdapter = VideoBannerAdapter(mutableListOf(), context!!)
//        rvPageBanner.layoutManager = layoutManager
//
////        val snapHelper = GravitySnapHelper(Gravity.CENTER)
////        snapHelper.attachToRecyclerView(rvPageBanner)
//        val snapHelper = GravitySnapHelper(Gravity.CENTER)
//        snapHelper.attachToRecyclerView(rvPageBanner)
//        snapHelper.maxFlingSizeFraction = 0.75f
//        snapHelper.snapLastItem = true
//
////        bannerAdapter.snapHelper.attachToRecyclerView(rvPageBanner)
//        rvPageBanner.adapter = bannerAdapter
//    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        pageAdapter = VideoV2Adapter(mutableListOf(), context!!)
        pageAdapter.setListener(object: VideoV2Adapter.Listener {
            override fun onModelClicked(model: VideoPresentationModel) {
                val intent = DetailsActivity.getCallingIntent(context!!, model)
                startActivity(intent)
            }
        })
        rvPageMain.layoutManager = layoutManager

        rvPageMain.adapter = pageAdapter
        rvPageMain.addItemDecoration(StickyHeaderItemDecoration(pageAdapter))

//        rvPageMain.addItemDecoration(StickyHeaderItemDecoration(adapter))
//        val layoutManager = GridLayoutManager(context!!, 2)
//        adapter = VideoAdapter(mutableListOf(), context!!)
//
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return when (adapter.getItemViewType(position)) {
//                    ViewTypeModel.VIEW_TYPE_LOADING.type -> 2
//                    ViewTypeModel.VIEW_TYPE_TITLE.type  -> 2
//                    else -> 1
//                }
//            }
//        }
//
//        rvPageMain.layoutManager = layoutManager
//
//        rvPageMain.adapter = adapter
//        rvPageMain.addItemDecoration(StickyHeaderItemDecoration(adapter))
//        endlessScrollListener  = object: EndlessRecyclerViewScrollListener(layoutManager ) {
//            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
//                Log.d("TestTag", "LoadMore")
//                mainViewModel.getViewEvent().callLatestVideosAPI()
//            }
//        }

//        rvPageMain.addOnScrollListener(endlessScrollListener)

    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        toolbar_layout.title = "New"
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener(object: Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                val id = item?.itemId
                if (id == R.id.action_search) {
                    val intent = SearchActivity.getCallingIntent(context!!)

                    startActivity(intent)
                    return true
                }
                return false
            }
        })
        setupAdapter()
//        setupBannerAdapter()

        mainViewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModelImpl::class.java)

        mainViewModel.getViewEvent().startScreen()
        mainViewModel.getViewData().getLatestVideos().observe(this, Observer {
            pageAdapter.addData(it)
//            adapter.addData(it)
//            bannerAdapter.updateData(it.toMutableList())
//            rvPageBanner.scrollToPosition(30)
//            rvPageBanner.smoothScrollBy(1, 0)
//            endlessScrollListener.resetState()
        })
    }

}