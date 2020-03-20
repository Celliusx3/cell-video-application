package com.cellstudio.cellvideo.presentation.screens.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : BaseInjectorActivity() {
    companion object {
        private const val EXTRA_TABS = "EXTRA_TABS"

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        intent?.let {}
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_search
    }

    override fun getRootView(): View {
        return clMainRoot
    }

    override fun onBindView() {
        super.onBindView()

        val fragment = SearchFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flVpMain, fragment)
        fragmentTransaction.commit()
    }


}