package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import javax.inject.Inject

class SourceSettingsViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                                      private val settingsInteractor: SettingsInteractor
): BaseViewModel(scheduler), SourceSettingsViewModel {
    private val dataSourceLiveData: MutableLiveData<Pair<List<DataSourcePresentationModel>, DataSourcePresentationModel?>> = MutableLiveData()
    private lateinit var input: SourceSettingsViewModel.Input

    override fun getViewData(): SourceSettingsViewModel.ViewData {
        return object: SourceSettingsViewModel.ViewData {
            override fun getLiveSources(): LiveData<Pair<List<DataSourcePresentationModel>, DataSourcePresentationModel?>> { return dataSourceLiveData }
            override val loading: LiveData<Boolean> = loadingLiveData
        }
    }

    override fun getOutput(): SourceSettingsViewModel.Output {
        return object: SourceSettingsViewModel.Output {}
    }

    override fun setInput(input: SourceSettingsViewModel.Input) {
        this.input = input
    }

    override fun getViewEvent(): SourceSettingsViewModel.ViewEvent {
        return object: SourceSettingsViewModel.ViewEvent {

            override fun startScreen() {
                loadingLiveData.value = true
                compositeDisposable.add(settingsInteractor.getDataSources()
                    .compose(applySchedulers())
                    .subscribe {
                        loadingLiveData.value = false
                        dataSourceLiveData.value = Pair(it.map { DataSourcePresentationModel.create(it) }, input.initialSource)
                    }
                )
            }
        }
    }
}