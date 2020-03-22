package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.data.repositories.m3urepository.M3URepository
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class LiveSourceMainViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                                      private val m3uRepository: M3URepository,
                                                      private val eYunZhuRepository: EYunZhuRepository): BaseViewModel(scheduler), LiveSourceMainViewModel {
    private val pageLiveData: MutableLiveData<List<LiveSourcePresentationModel>> = MutableLiveData()

    override fun getViewData(): LiveSourceMainViewModel.ViewData {
        return object: LiveSourceMainViewModel.ViewData {
            override fun getLatestLiveSource(): LiveData<List<LiveSourcePresentationModel>> = pageLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
        }
    }

    override fun getViewEvent(): LiveSourceMainViewModel.ViewEvent {
        return object: LiveSourceMainViewModel.ViewEvent {
            private fun callLiveSourceAPI(): Observable<List<LiveSourcePresentationModel>> {
//                return eYunZhuRepository.getLiveSource()
//                    .map { it.map { LiveSourcePresentationModel.create(it) }}
                return m3uRepository.getLiveSource("my")
                    .map { it.map { LiveSourcePresentationModel.create(it) }}
            }

            override fun startScreen() {
                loadingLiveData.value = true
                compositeDisposable.add(callLiveSourceAPI()
                    .compose(applySchedulers())
                    .subscribe {
                        pageLiveData.value = it
                    }
                )
            }
        }

    }
}