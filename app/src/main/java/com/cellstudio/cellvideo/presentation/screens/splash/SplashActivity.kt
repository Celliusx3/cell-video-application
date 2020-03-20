package com.cellstudio.cellvideo.presentation.screens.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.interactor.viewmodel.splash.SplashViewModel
import com.cellstudio.cellvideo.interactor.viewmodel.splash.SplashViewModelImpl
import com.cellstudio.cellvideo.presentation.base.BaseInjectorActivity
import com.cellstudio.cellvideo.presentation.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseInjectorActivity() {
    private lateinit var viewModel: SplashViewModel
    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun getRootView(): View {
        return clSplashRoot
    }

    override fun onBindView() {
        super.onBindView()
        viewModel = ViewModelProvider(this,viewModelFactory).get(SplashViewModelImpl::class.java)
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)
        viewModel.getOutput().navigateToMain().observe(this, Observer {
            val intent = MainActivity.getCallingIntent(this, it)

            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        })

        viewModel.getViewEvent().startScreen()
    }
}