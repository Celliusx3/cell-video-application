package com.cellstudio.cellvideo.presentation.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected abstract fun getRootView(): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onGetInputData(savedInstanceState)
        onSetContentView()
        onInject()
        onBindView()
        onBindData(getRootView(), savedInstanceState)
    }

    protected open fun onSetContentView() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource())
        }
    }

    protected open fun onBindView() {}

    protected open fun onInject() {}

    protected open fun onBindData(view: View?, savedInstanceState: Bundle?) {
        if (savedInstanceState != null)
            return
    }

    protected open fun onGetInputData(savedInstanceState: Bundle?) {}
}