package com.cellstudio.cellvideo.presentation.screens.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.search.SearchViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.search.SearchViewModelImpl
import com.cellstudio.cellvideo.presentation.adapters.SearchAdapter
import com.cellstudio.cellvideo.presentation.adapters.SearchAdapter.Companion.LOADING_TYPE
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import com.cellstudio.cellvideo.presentation.components.DelayTextWatcher
import com.cellstudio.cellvideo.presentation.screens.details.DetailsActivity
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: BaseInjectorFragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var pageAdapter: SearchAdapter

    private var inputMethodManager: InputMethodManager? = null

    companion object {
        fun newInstance(): SearchFragment {
            val bundle = Bundle()

            val fragment = SearchFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_search
    }

    private fun setupAdapter() {
        val layoutManager = GridLayoutManager(context, 2)
        pageAdapter = SearchAdapter(mutableListOf(), context!!)
        pageAdapter.setListener(object: SearchAdapter.Listener {
            override fun onModelClicked(model: VideoPresentationModel) {
                val intent = DetailsActivity.getCallingIntent(context!!, model)
                startActivity(intent)
            }
        })

        rvSearchMain.layoutManager = layoutManager
        rvSearchMain.adapter = pageAdapter
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pageAdapter.getItemViewType(position)) {
                    LOADING_TYPE -> 2
                    else -> 1
                }
            }
        }

        rvSearchMain.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            var isKeyboardDismissedByScroll = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        if (!isKeyboardDismissedByScroll) {
                            getInputMethodManager()?.hideSoftInputFromWindow(view?.windowToken, 0);
                            isKeyboardDismissedByScroll = !isKeyboardDismissedByScroll;
                        }
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        isKeyboardDismissedByScroll = false
                    }

                }
            }

        })
    }

    /**
     * Returns an [InputMethodManager]
     *
     * @return input method manager
     */
    private fun getInputMethodManager(): InputMethodManager? {
        if (null == inputMethodManager) {
            inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
        return inputMethodManager
    }

    override fun onBindView(view: View) {
        super.onBindView(view)

        toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        setupAdapter()

        etSearchInput.addTextChangedListener(DelayTextWatcher(
            object: DelayTextWatcher.Listener {
                override fun onTextChanged() {}
                override fun afterTextChanged(editable: Editable?) {
                    editable?.let {
                        if (it.toString().isNotEmpty()) {
                            activity?.runOnUiThread {
                                searchViewModel.getViewEvent().search(it.toString())
                            }
                        }
                    }
                }
            }
        ))



        searchViewModel = ViewModelProvider(this,viewModelFactory).get(SearchViewModelImpl::class.java)

        searchViewModel.getViewEvent().search("溏心")
        searchViewModel.getViewData().getLatestVideos().observe(this, Observer {
            it.videoList?.let {
                pageAdapter.addData(it)
            }
        })
    }

}