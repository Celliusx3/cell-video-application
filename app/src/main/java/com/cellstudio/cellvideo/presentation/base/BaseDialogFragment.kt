package com.cellstudio.cellvideo.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment: DialogFragment() {
    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onGetInputData()
        onInject()
        onSetupViewModel()
        onBindView(view)
        onBindData(view)
        setupUI()
        observeResponse()
    }

    protected open fun onInject() {}

    protected open fun onBindView(view: View) {}

    protected open fun onGetInputData() {}

    protected open fun onSetupViewModel() {}

    protected open fun onBindData(view: View) {}

    protected open fun setupUI() {}

    protected open fun observeResponse() {}
}