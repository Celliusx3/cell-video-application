package com.cellstudio.cellvideo.interactor.viewmodel.search

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListPresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface SearchViewModel : ViewModel {
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData

    interface ViewEvent : ViewModel.ViewEvent {
        fun search(name: String)
    }

    interface ViewData : ViewModel.ViewData {
        fun getLatestVideos(): LiveData<VideoListPresentationModel>
    }

    interface Output : ViewModel.Output

    interface Input : ViewModel.Input
}
