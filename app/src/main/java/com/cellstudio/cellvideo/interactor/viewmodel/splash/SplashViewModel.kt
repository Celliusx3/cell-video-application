package com.cellstudio.cellvideo.interactor.viewmodel.splash

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.PagePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface SplashViewModel : ViewModel {
    fun getOutput(): Output
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
    }

    interface ViewData : ViewModel.ViewData

    interface Output : ViewModel.Output {
        fun navigateToMain(): LiveData<List<PagePresentationModel>>
    }

    interface Input : ViewModel.Input
}