package com.cellstudio.cellvideo.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    protected val disposable: CompositeDisposable = CompositeDisposable()

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onGetInputData()
        onInject()
        onBindView(view)
        onBindData(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    protected open fun onBindView(view: View) {}

    protected open fun onInject() {}

    protected open fun onBindData(view: View?) {}

    protected open fun onGetInputData() {}
}