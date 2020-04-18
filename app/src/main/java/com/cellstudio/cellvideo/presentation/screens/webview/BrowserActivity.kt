package com.cellstudio.cellvideo.presentation.screens.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_browser.*

class BrowserActivity : BaseActivity() {

    private var url: String? = null

    override fun getRootView(): View {
        return rlBrowserMain
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_browser
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        if (intent != null)
            url = intent.getStringExtra(EXTRA_URL)
    }


    override fun onBindView() {
        super.onBindView()

        createWebView()
        loadUrl(url)
    }

    private fun createWebView() {
        val webSettings = wvBrowserWeb.settings
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
    }

    private fun loadUrl(url: String?) {
        wvBrowserWeb.loadUrl(url)
    }

    companion object {
        private const val EXTRA_URL = "EXTRA_URL"

        fun getCallingIntent(
            context: Context,
            url: String
        ): Intent {
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            return intent
        }
    }
}