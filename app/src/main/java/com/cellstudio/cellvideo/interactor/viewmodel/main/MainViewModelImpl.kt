package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.data.repositories.configrepository.ConfigRepository
import com.cellstudio.cellvideo.data.repositories.jikerepository.JikeRepository
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoListViewType
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                            private val configRepository: ConfigRepository,
                                            private val jikeRepository: JikeRepository): BaseViewModel(scheduler), MainViewModel {
    private val pageLiveData: MutableLiveData<VideoListPresentationModel> = MutableLiveData()

    override fun getViewData(): MainViewModel.ViewData {
        return object: MainViewModel.ViewData {
            override fun getLatestVideos(): LiveData<VideoListPresentationModel> = pageLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
        }
    }

    override fun getViewEvent(): MainViewModel.ViewEvent {
        return object: MainViewModel.ViewEvent {
            private fun callVideosBannerAPI(): Observable<VideoListPresentationModel> {
                return jikeRepository.getVideosBanner("new", 6)
                    .map { it.map { VideoPresentationModel.create(it) } }
                    .map { VideoListPresentationModel.create(null, it, VideoListViewType.VIEW_TYPE_LIST) }
            }

            private fun callVideosListsAPI(): Observable<VideoListPresentationModel> {
                val ggTest = mutableListOf<String>()
                ggTest.addAll(configRepository.getJikeVideoTypes())
                ggTest.addAll(configRepository.getJikeVideoTypes())
                ggTest.addAll(configRepository.getJikeVideoTypes())
                return Observable.merge(ggTest.map { title ->
                    jikeRepository.getVideosList(title, 10)
                        .map { it.map { VideoPresentationModel.create(it) }}
                        .map { it.toMutableList()}
                        .map { it.toList() }
                        .map { VideoListPresentationModel.create(title, it, VideoListViewType.VIEW_TYPE_RAIL) }
                })
            }

            override fun startScreen() {
                loadingLiveData.value = true
                compositeDisposable.add(Observable.merge( callVideosBannerAPI(), callVideosListsAPI())
                    .compose(applySchedulers())
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