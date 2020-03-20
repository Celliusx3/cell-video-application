package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface MainViewModel : ViewModel {
//    val output: Observable<Output>
//    val input: Input
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
    }

    interface ViewData : ViewModel.ViewData {
        fun getLatestVideos(): LiveData<VideoListPresentationModel>
    }

    interface Output : ViewModel.Output

    interface Input : ViewModel.Input
}
