package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.interactor.model.domainmodel.DataSourceModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import javax.inject.Inject

class SourceSettingsViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                                      private val settingsInteractor: SettingsInteractor
): BaseViewModel(scheduler), SourceSettingsViewModel {
    private val dataSourceLiveData: MutableLiveData<Pair<List<DataSourcePresentationModel>, DataSourcePresentationModel?>> = MutableLiveData()
    private val removedSourceLiveData: MutableLiveData<String> = MutableLiveData()
    private val addedSourceLiveData: MutableLiveData<DataSourcePresentationModel> = MutableLiveData()
    private lateinit var input: SourceSettingsViewModel.Input

    override fun getViewData(): SourceSettingsViewModel.ViewData {
        return object: SourceSettingsViewModel.ViewData {
            override fun getLiveSources(): LiveData<Pair<List<DataSourcePresentationModel>, DataSourcePresentationModel?>> { return dataSourceLiveData }
            override fun getRemovedSource(): LiveData<String> { return removedSourceLiveData }
            override fun getAddedSource(): LiveData<DataSourcePresentationModel> { return addedSourceLiveData }
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

            override fun addSource(model: DataSourcePresentationModel) {
                loadingLiveData.value = true
                compositeDisposable.add(
                    settingsInteractor.addNewSource(DataSourceModel(model.id, model.label, model.url, model.isEditable))
                        .compose(applySchedulers())
                        .subscribe {
                            addedSourceLiveData.value = model
                        }
                )
            }

            override fun removeSource(id: String) {
                loadingLiveData.value = true
                compositeDisposable.add(
                    settingsInteractor.removeNewSource(id)
                        .compose(applySchedulers())
                        .subscribe {
                            removedSourceLiveData.value = id
                        }
                )
            }

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