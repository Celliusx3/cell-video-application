package com.cellstudio.cellvideo.interactor.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.data.repositories.jikerepository.JikeRepository
import com.cellstudio.cellvideo.interactor.model.presentationmodel.PagePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                              private val eYunZhuRepository: EYunZhuRepository,
                                              private val jikeRepository: JikeRepository
): BaseViewModel(scheduler), SplashViewModel {

    private val navigateToMainScreenLiveData: MutableLiveData<List<PagePresentationModel>> = MutableLiveData()

    override fun getOutput(): SplashViewModel.Output {
        return object: SplashViewModel.Output {
            override fun navigateToMain(): LiveData<List<PagePresentationModel>> {
                return navigateToMainScreenLiveData
            }
        }
    }

    override fun getViewEvent(): SplashViewModel.ViewEvent = object: SplashViewModel.ViewEvent {
        override fun startScreen() {
            loadingLiveData.value = true
            compositeDisposable.add(
                Observable.timer(1, TimeUnit.SECONDS)
                    .compose(applySchedulers())
                    .subscribe({
                        navigateToMainScreenLiveData.value = eYunZhuRepository.getPages().map { PagePresentationModel.create(it) }
                    }, {})
            )

        }
    }
    override fun getViewData(): SplashViewModel.ViewData = object: SplashViewModel.ViewData {
        override val loading: LiveData<Boolean> = loadingLiveData
    }

}