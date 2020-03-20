package com.cellstudio.cellvideo.presentation.base

import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject



abstract class BaseInjectorActivity: BaseActivity(), HasAndroidInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>


    override fun onInject() {
        super.onInject()
        AndroidInjection.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return childFragmentInjector
    }

}