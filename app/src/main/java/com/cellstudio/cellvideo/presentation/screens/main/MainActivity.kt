package com.cellstudio.cellvideo.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.model.presentationmodel.PagePresentationModel
import com.cellstudio.cellvideo.presentation.adapters.MainPagerAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation_fragment.*

class MainActivity : BaseInjectorActivity() {

    private lateinit var tabs: List<PagePresentationModel>

    private lateinit var mainPagerAdapter: MainPagerAdapter

    companion object {
        private const val EXTRA_TABS = "EXTRA_TABS"

        fun getCallingIntent(context: Context, tabs: List<PagePresentationModel>): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_TABS, tabs as ArrayList<PagePresentationModel>)
            return intent
        }
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        intent?.let {
            tabs = intent.getParcelableArrayListExtra(EXTRA_TABS) ?: listOf()
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_bottom_navigation_fragment

    }

    override fun getRootView(): View {
        return rlRoot
    }

    override fun onBindView() {
        super.onBindView()

        setupMainPagerAdapter(tabs)
        setupBottomNavigationView(tabs)
    }

    private fun setupBottomNavigationView(pages: List<PagePresentationModel>) {
        if (bnvRoot == null)
            return

        if (pages.size <= 1) {
            bnvRoot.visibility = View.GONE
            return
        }

        for (page in pages) {
            bnvRoot.menu.add(Menu.NONE, page.id, Menu.NONE, page.name).setIcon(page.icon)
        }

        bnvRoot.setOnNavigationItemSelectedListener { item ->
            this.setPage(item.itemId)
            true
        }
    }

    private fun setPage(pageId: Int) {
        val pagePosition = mainPagerAdapter.getPagePositionById(pageId)
        dsvpRoot.currentItem = pagePosition
    }

    private fun setupMainPagerAdapter(pages: List<PagePresentationModel>) {
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager, pages)
        dsvpRoot.adapter = mainPagerAdapter
        dsvpRoot.offscreenPageLimit = pages.size
    }


}
