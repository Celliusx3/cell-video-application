package com.cellstudio.cellvideo.presentation.screens.settings

import android.content.Context
import android.content.Intent
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.model.presentationmodel.PagePresentationModel
import com.cellstudio.cellvideo.presentation.adapters.MainPagerAdapter
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation_fragment.*

class SettingsActivity : BaseInjectorActivity() {

    private lateinit var tabs: List<PagePresentationModel>

    private lateinit var mainPagerAdapter: MainPagerAdapter

    companion object {
        private const val EXTRA_TABS = "EXTRA_TABS"

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_single_fragment
    }

    override fun getRootView(): View {
        return rlRoot
    }

    override fun onBindView() {
        super.onBindView()
        val fragment = SettingsFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flVpMain, fragment)
        fragmentTransaction.commit()
    }

}