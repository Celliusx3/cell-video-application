package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface LiveSourceMainViewModel : ViewModel {
    //    val output: Observable<Output>
//    val input: Input
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
    }

    interface ViewData : ViewModel.ViewData {
        fun getLatestLiveSource(): LiveData<List<LiveSourcePresentationModel>>
    }

    interface Output : ViewModel.Output

    interface Input : ViewModel.Input
}
