package com.cellstudio.cellvideo.presentation.screens.livesource

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import kotlinx.android.synthetic.main.activity_details.*

class LiveSourceActivity : BaseInjectorActivity() {

    private lateinit var url: String
    private lateinit var title: String

    companion object {
        private const val EXTRA_LIVE_SOURCE_URL = "EXTRA_LIVE_SOURCE_URL"
        private const val EXTRA_LIVE_SOURCE_TITLE = "EXTRA_LIVE_SOURCE_TITLE"

        fun getCallingIntent(context: Context, url: String, title: String): Intent {
            val intent = Intent(context, LiveSourceActivity::class.java)
            intent.putExtra(EXTRA_LIVE_SOURCE_URL, url)
            intent.putExtra(EXTRA_LIVE_SOURCE_TITLE, title)
            return intent
        }
    }

    override fun onSetContentView() {
        super.onSetContentView()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        intent?.let {
            url = intent.getStringExtra(EXTRA_LIVE_SOURCE_URL) ?: ""
            title = intent.getStringExtra(EXTRA_LIVE_SOURCE_TITLE) ?: ""
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_details
    }

    override fun getRootView(): View {
        return llDetailsRoot
    }

    override fun onBindView() {
        super.onBindView()
        val fragment = LiveSourceFragment.newInstance(url, title)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flVpMain, fragment)
        fragmentTransaction.commit()
    }
}
