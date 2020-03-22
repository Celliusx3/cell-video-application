package com.cellstudio.cellvideo.interactor.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.constants.SharedPrefConstants
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.domain.interactor.splash.SplashInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.PagePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                              private val storage: StorageService,
                                              private val splashInteractor: SplashInteractor
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
        private fun setupInitialStorageService(): DataSource {
            val start = storage.getString(SharedPrefConstants.start)
            return if (start.isNullOrEmpty()) {
                storage.setString(SharedPrefConstants.start, DataSource.M3U.name)
                DataSource.M3U
            } else {
                DataSource.valueOf(start)
            }
        }

//        private fun getPagePresentationModels(start: DataSource): List<PagePresentationModel> {
//            return when (start) {
//                DataSource.EYUNZHU -> eYunZhuRepository.getPages().map { PagePresentationModel.create(it) }
//                DataSource.JIKE -> jikeRepository.getPages().map { PagePresentationModel.create(it) }
//                else -> m3uRepository.getPages().map { PagePresentationModel.create(it) }
//            }
//        }

        override fun startScreen() {
            loadingLiveData.value = true
            compositeDisposable.add(
                Observable.timer(1, TimeUnit.SECONDS)
                    .compose(applySchedulers())
                    .subscribe({
                        navigateToMainScreenLiveData.value = splashInteractor.getPageModels().map { PagePresentationModel.create(it)}
                    }, {})
            )
        }
    }
    override fun getViewData(): SplashViewModel.ViewData = object: SplashViewModel.ViewData {
        override val loading: LiveData<Boolean> = loadingLiveData
    }

}