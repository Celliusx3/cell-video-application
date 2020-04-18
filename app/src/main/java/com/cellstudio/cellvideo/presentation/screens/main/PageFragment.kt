package com.cellstudio.cellvideo.presentation.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.FragmentPageBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.FilterListPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.FilterPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.main.PageViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.main.PageViewModelImpl
import com.cellstudio.cellvideo.presentation.adapters.LiveSourceAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import com.cellstudio.cellvideo.presentation.screens.SingleSelectionBottomSheetFragment
import com.cellstudio.cellvideo.presentation.screens.alertdialogs.DialogUtils
import com.cellstudio.cellvideo.presentation.screens.details.DetailsActivity
import com.cellstudio.cellvideo.presentation.screens.livesource.LiveSourceActivity
import com.cellstudio.cellvideo.presentation.screens.loading.LoadingDialogFragment
import com.cellstudio.cellvideo.presentation.screens.settings.SettingsActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_page.*


class PageFragment: BaseInjectorFragment() {
    private lateinit var pageViewModel: PageViewModel
    private lateinit var adapter: LiveSourceAdapter
    private var filter: FilterListPresentationModel?= null
    private lateinit var map: Map<String, String>
    private lateinit var title: String

    private val loadingDialogFragment = LoadingDialogFragment.newInstance()

    private val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    companion object {
        const val EXTRA_FILTER_PRESENTATION_MODEL = "EXTRA_FILTER_PRESENTATION_MODEL"
        const val EXTRA_DATAS = "EXTRA_DATAS"
        const val EXTRA_TITLE = "EXTRA_TITLE"

        fun newInstance(title: String, model: FilterListPresentationModel?, map: HashMap<String, String>): PageFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_FILTER_PRESENTATION_MODEL, model)
            bundle.putSerializable(EXTRA_DATAS, map)
            bundle.putString (EXTRA_TITLE, title)
            val fragment = PageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_page
    }

    private fun setupAdapter() {
       val test =  DialogUtils.showLoadingDialog(context!!)
        test.show()
//        val layoutParams = WindowManager.LayoutParams()
//        layoutParams.copyFrom(test.getWindow()?.getAttributes())
//        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
//        test.getWindow()?.setAttributes(layoutParams)
        adapter = LiveSourceAdapter(mutableListOf(), context!!)
        adapter.setListener(object: LiveSourceAdapter.Listener {
            override fun onModelClicked(model: LiveSourcePresentationModel) {
                Log.d("Test",model.url)
                if (model.type == LiveSourcePresentationModel.ContentType.LIVESOURCE) {
                    val intent = LiveSourceActivity.getCallingIntent(context!!, model.url, model.name)
                    startActivity(intent)
                } else {
                    pageViewModel.getViewEvent().onGetDetails(model.id)
                }
            }
        })
        rvPageMain.layoutManager = linearLayoutManager

        rvPageMain.adapter = adapter
    }

    override fun onSetupViewModel() {
        super.onSetupViewModel()
        pageViewModel = ViewModelProvider(this,viewModelFactory).get(PageViewModelImpl::class.java)
        pageViewModel.setInput(object: PageViewModel.Input {
            override val filter: FilterListPresentationModel? = this@PageFragment.filter
            override val data: Map<String, String> = this@PageFragment.map
        })
    }

    override fun onGetInputData() {
        super.onGetInputData()
        filter = arguments?.getParcelable<FilterListPresentationModel>(EXTRA_FILTER_PRESENTATION_MODEL)
        map = arguments?.getSerializable(EXTRA_DATAS) as HashMap<String, String>
        title = arguments?.getString(EXTRA_TITLE) ?: ""
    }

    override fun onBindData(view: View?) {
        super.onBindData(view)
        pageViewModel.getViewData().getLiveSources().observe(this, Observer {
            if (it.second) {
                adapter.updateData(it.first.toMutableList())
            } else {
                adapter.addDatas(it.first)
            }
        })

        pageViewModel.getViewData().loading.observe(this, Observer {
            adapter.setLoading(it)
        })

        pageViewModel.getViewData().getDetails().observe(this, Observer {
            val intent = DetailsActivity.getCallingIntent(context!!, it)
            startActivity(intent)
        })

        pageViewModel.getViewData().getOpenFilterDialog().observe(this, Observer {
            val fragment = SingleSelectionBottomSheetFragment<FilterPresentationModel>()
            fragment.initialSelection = it.second
            fragment.selectionsList = it.first
            fragment.listener = object: SingleSelectionBottomSheetFragment.Listener<FilterPresentationModel> {
                override fun onFragmentReady() {}
                override fun onHide() {}
                override fun onSelected(model: FilterPresentationModel) {
                    pageViewModel.getViewEvent().onFilterSelected(model)
                }
            }
            fragment.show(childFragmentManager, null)
        })

//        pageViewModel.getViewData().getIsGridView().observe(this, Observer {
//            if (it) {
//                val layoutManager = GridLayoutManager(context!!, 2)
//                rvPageMain.layoutManager = layoutManager
//            } else {
//                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                rvPageMain.layoutManager = layoutManager
//            }
//        })


    }

    override fun onBindView(view: View) {
        super.onBindView(view)

        val binding = DataBindingUtil.bind<FragmentPageBinding>(view)
        binding?.viewModel = pageViewModel
        binding?.lifecycleOwner = this

        ctlToolbar.title = title
        setupAdapter()
        setupNestedScrollView()
        setupToolbar(false)
//        toolbar.setOnMenuItemClickListener(object: Toolbar.OnMenuItemClickListener {
//            override fun onMenuItemClick(item: MenuItem?): Boolean {
//                val id = item?.itemId
//                if (id == R.id.action_search) {
//                    val intent = SearchActivity.getCallingIntent(context!!)
//
//                    startActivity(intent)
//                    return true
//                }
//                return false
//            }
//        })

        ivProfile.setOnClickListener {
            val intent = SettingsActivity.getCallingIntent(context!!)
            startActivity(intent)
        }

        pageViewModel.getViewEvent().startScreen()


    }

    private fun setupToolbar(isSearchActive: Boolean) {
        if (!isSearchActive) {
            return
        }
        toolbar.inflateMenu(R.menu.menu_main)
        val spaceToChange = resources.getDimensionPixelSize(R.dimen.space_to_change)
        val searchMenuSize = resources.getDimensionPixelSize(R.dimen.space_to_translate)
        ablToolbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val startPoint = appBarLayout!!.totalScrollRange - spaceToChange
            var percentage = 0f
            if (Math.abs(verticalOffset) - startPoint > 0) {
                percentage = (Math.abs(verticalOffset) - startPoint).toFloat() / spaceToChange
            }
            flTest.translationX = -searchMenuSize * (percentage)
        })
    }

    private fun setupNestedScrollView() {
        // Setting loadMore for RecyclerView inside NestedScrollView
        nsvPageMain.setOnScrollChangeListener { nsv: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            // Needs a check on ItemCount in target adapter due to multiple RecyclerViews in same root
            nsv?.let {
                if(it.getChildAt(it.childCount - 1) != null) {
                    if ((scrollY >= (it.getChildAt(it.childCount - 1).measuredHeight - it.measuredHeight)) &&
                        scrollY > oldScrollY && !adapter.getLoading()) {
                        val visibleItemCount = linearLayoutManager.childCount;
                        val totalItemCount = linearLayoutManager.itemCount;
                        val pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            pageViewModel.getViewEvent().onLoadMore()
                        }
                    }
                }
            }
        }
    }
}

//class PageFragment: BaseInjectorFragment() {
//
//    private lateinit var mainViewModel: MainViewModel
////    private lateinit var adapter: VideoAdapter
//    private lateinit var bannerAdapter: VideoBannerAdapter
//    private lateinit var pageAdapter: VideoV2Adapter
////    private lateinit var endlessScrollListener: EndlessRecyclerViewScrollListener
//
//
//    companion object {
//        fun newInstance(): PageFragment {
//            val bundle = Bundle()
//
//            val fragment = PageFragment()
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//
//    override fun getLayoutResource(): Int {
//        return com.cellstudio.cellvideo.R.layout.fragment_page
//    }
//
////    private fun setupBannerAdapter() {
//////        val layoutManager = CenterZoomLinearLayoutManager(context!!)
////        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
////        bannerAdapter = VideoBannerAdapter(mutableListOf(), context!!)
////        rvPageBanner.layoutManager = layoutManager
////
//////        val snapHelper = GravitySnapHelper(Gravity.CENTER)
//////        snapHelper.attachToRecyclerView(rvPageBanner)
////        val snapHelper = GravitySnapHelper(Gravity.CENTER)
////        snapHelper.attachToRecyclerView(rvPageBanner)
////        snapHelper.maxFlingSizeFraction = 0.75f
////        snapHelper.snapLastItem = true
////
//////        bannerAdapter.snapHelper.attachToRecyclerView(rvPageBanner)
////        rvPageBanner.adapter = bannerAdapter
////    }
//
//    private fun setupAdapter() {
//        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        pageAdapter = VideoV2Adapter(mutableListOf(), context!!)
//        pageAdapter.setListener(object: VideoV2Adapter.Listener {
//            override fun onModelClicked(model: VideoPresentationModel) {
//                val intent = DetailsActivity.getCallingIntent(context!!, model)
//                startActivity(intent)
//            }
//        })
//        rvPageMain.layoutManager = layoutManager
//
//        rvPageMain.adapter = pageAdapter
//        rvPageMain.addItemDecoration(StickyHeaderItemDecoration(pageAdapter))
//
////        rvPageMain.addItemDecoration(StickyHeaderItemDecoration(adapter))
////        val layoutManager = GridLayoutManager(context!!, 2)
////        adapter = VideoAdapter(mutableListOf(), context!!)
////
////        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
////            override fun getSpanSize(position: Int): Int {
////                return when (adapter.getItemViewType(position)) {
////                    ViewTypeModel.VIEW_TYPE_LOADING.type -> 2
////                    ViewTypeModel.VIEW_TYPE_TITLE.type  -> 2
////                    else -> 1
////                }
////            }
////        }
////
////        rvPageMain.layoutManager = layoutManager
////
////        rvPageMain.adapter = adapter
////        rvPageMain.addItemDecoration(StickyHeaderItemDecoration(adapter))
////        endlessScrollListener  = object: EndlessRecyclerViewScrollListener(layoutManager ) {
////            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
////                Log.d("TestTag", "LoadMore")
////                mainViewModel.getViewEvent().callLatestVideosAPI()
////            }
////        }
//
////        rvPageMain.addOnScrollListener(endlessScrollListener)
//
//    }
//
//    override fun onBindView(view: View) {
//        super.onBindView(view)
//        toolbar.inflateMenu(R.menu.menu_main)
//        toolbar.setOnMenuItemClickListener(object: Toolbar.OnMenuItemClickListener {
//            override fun onMenuItemClick(item: MenuItem?): Boolean {
//                val id = item?.itemId
//                if (id == R.id.action_search) {
//                    val intent = SearchActivity.getCallingIntent(context!!)
//
//                    startActivity(intent)
//                    return true
//                }
//                return false
//            }
//        })
//        setupAdapter()
////        setupBannerAdapter()
//
//        mainViewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModelImpl::class.java)
//
//        mainViewModel.getViewEvent().startScreen()
//        mainViewModel.getViewData().getLatestVideos().observe(this, Observer {
//            pageAdapter.addData(it)
////            adapter.addData(it)
////            bannerAdapter.updateData(it.toMutableList())
////            rvPageBanner.scrollToPosition(30)
////            rvPageBanner.smoothScrollBy(1, 0)
////            endlessScrollListener.resetState()
//        })
//    }
//
//}