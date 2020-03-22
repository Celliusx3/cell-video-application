package com.cellstudio.cellvideo.presentation.screens.settings

import android.content.Context
import android.content.Intent
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation_fragment.*

class SettingsActivity : BaseInjectorActivity() {
    companion object {
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
        fragmentTransaction.replace(R.id.flRoot, fragment)
        fragmentTransaction.commit()
    }

}