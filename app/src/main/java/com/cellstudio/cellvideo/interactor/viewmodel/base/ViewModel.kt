package com.cellstudio.cellvideo.interactor.viewmodel.base

import androidx.lifecycle.LiveData


interface ViewModel {
    interface ViewEvent

    interface ViewData {
        val loading: LiveData<Boolean>
    }

    interface Output

    interface Input
}
