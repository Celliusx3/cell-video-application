package com.cellstudio.cellvideo.interactor.viewmodel.details

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface DetailsViewModel : ViewModel {
    //    val output: Observable<Output>
//    val input: Input
    fun setInput(input: Input)
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
    }

    interface ViewData : ViewModel.ViewData {
        fun getDetails(): LiveData<VideoPresentationModel>
    }

    interface Output : ViewModel.Output

    interface Input : ViewModel.Input {
        val id: Int
    }
}
