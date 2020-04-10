package com.cellstudio.cellvideo.presentation.base

import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseInjectorDialogFragment: BaseDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onInject() {
        super.onInject()
        AndroidSupportInjection.inject(this)
    }
}