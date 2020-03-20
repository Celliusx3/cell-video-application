package com.cellstudio.cellvideo.presentation.screens.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.viewmodel.main.LiveSourceMainViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.main.LiveSourceMainViewModelImpl
import com.cellstudio.cellvideo.presentation.adapters.LiveSourceAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorFragment
import com.cellstudio.cellvideo.presentation.screens.livesource.LiveSourceActivity
import com.cellstudio.cellvideo.presentation.screens.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_page.*

class TextPageFragment: BaseInjectorFragment() {
    private lateinit var liveSourceMainViewModel: LiveSourceMainViewModel

    private lateinit var adapter: LiveSourceAdapter

    companion object {
        fun newInstance(): TextPageFragment {
            val bundle = Bundle()

            val fragment = TextPageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_page
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = LiveSourceAdapter(mutableListOf(), context!!)
        adapter.setListener(object: LiveSourceAdapter.Listener {
            override fun onModelClicked(model: String) {
                val intent = LiveSourceActivity.getCallingIntent(context!!, model)
                startActivity(intent)
            }
        })
        rvPageMain.layoutManager = layoutManager

        rvPageMain.adapter = adapter
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        toolbar_layout.title = "Source"
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
        liveSourceMainViewModel = ViewModelProvider(this,viewModelFactory).get(LiveSourceMainViewModelImpl::class.java)

        liveSourceMainViewModel.getViewEvent().startScreen()
        liveSourceMainViewModel.getViewData().getLatestLiveSource().observe(this, Observer {
            adapter.updateData(it[0].videoUrl?.toMutableList()?: mutableListOf())
//            adapter.addData(it)
//            bannerAdapter.updateData(it.toMutableList())
//            rvPageBanner.scrollToPosition(30)
//            rvPageBanner.smoothScrollBy(1, 0)
//            endlessScrollListener.resetState()
        })
    }

}