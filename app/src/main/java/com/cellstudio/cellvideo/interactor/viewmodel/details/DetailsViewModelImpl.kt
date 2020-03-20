package com.cellstudio.cellvideo.interactor.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.data.repositories.configrepository.ConfigRepository
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import javax.inject.Inject

class DetailsViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                            private val configRepository: ConfigRepository,
                                            private val eYunZhuRepository: EYunZhuRepository
): BaseViewModel(scheduler), DetailsViewModel {
    private lateinit var input: DetailsViewModel.Input

    private val detailsLiveData = MutableLiveData<VideoPresentationModel>()

    override fun setInput(input: DetailsViewModel.Input) {
        this.input = input
    }

    override fun getViewEvent(): DetailsViewModel.ViewEvent {
        return object: DetailsViewModel.ViewEvent {
            override fun startScreen() {
                loadingLiveData.value = true
                compositeDisposable.add( eYunZhuRepository.getDetails(input.id)
                    .map { VideoPresentationModel.create(it)}
                    .compose(applySchedulers())
                    .subscribe {
                        detailsLiveData.value = it
                    }
                )
            }
        }
    }

    override fun getViewData(): DetailsViewModel.ViewData {
        return object: DetailsViewModel.ViewData {
            override fun getDetails() = detailsLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
        }
    }
}