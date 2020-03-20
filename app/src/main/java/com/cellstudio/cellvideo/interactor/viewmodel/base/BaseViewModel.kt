package com.cellstudio.cellvideo.interactor.viewmodel.base

import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected val scheduler: SchedulerProvider) : androidx.lifecycle.ViewModel(),ViewModel {
    protected val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}
