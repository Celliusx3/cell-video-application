package com.cellstudio.cellvideo.interactor.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListViewType
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import javax.inject.Inject

class SearchViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                            private val eYunZhuRepository: EYunZhuRepository
): BaseViewModel(scheduler), SearchViewModel {
    private val pageLiveData: MutableLiveData<VideoListPresentationModel> = MutableLiveData()

    override fun getViewData(): SearchViewModel.ViewData {
        return object: SearchViewModel.ViewData {
            override fun getLatestVideos(): LiveData<VideoListPresentationModel> = pageLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
        }
    }

    override fun getViewEvent(): SearchViewModel.ViewEvent {
        return object: SearchViewModel.ViewEvent {
            override fun search(name: String) {
                loadingLiveData.value = true
                compositeDisposable.add(
                    eYunZhuRepository.search(name, 25, 0)
                        .compose(applySchedulers())
                        .map { it.map { VideoPresentationModel.create(it) }}
                        .map { it.toMutableList()}
                        .map { it.toList() }
                        .map { VideoListPresentationModel.create("", it, VideoListViewType.VIEW_TYPE_RAIL) }
                        .subscribe {
                            if (!it.videoList.isNullOrEmpty()) {
                                pageLiveData.value = it
                            }
                        }
                )
            }
        }

    }
}