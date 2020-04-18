package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.interactor.model.domainmodel.DataSourceModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                                private val settingsInteractor: SettingsInteractor
): BaseViewModel(scheduler), SettingsViewModel {
    private val dataSourceLiveData: MutableLiveData<DataSourcePresentationModel> = MutableLiveData()
    private val openSourceSelectionDialogLiveData: MutableLiveData<DataSourcePresentationModel> = MutableLiveData()
    private val resetLiveData: MutableLiveData<Unit> = MutableLiveData()
    private val webLiveData: MutableLiveData<String> = MutableLiveData()


    override fun getViewData(): SettingsViewModel.ViewData {
        return object: SettingsViewModel.ViewData {
            override fun getDataSource(): LiveData<String> = Transformations.map (dataSourceLiveData) { it.label }
            override fun getOpenSourceSelectionDialog(): LiveData<DataSourcePresentationModel> = openSourceSelectionDialogLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
            override fun getOpenWebView(): LiveData<String> = webLiveData
        }
    }

    override fun getOutput(): SettingsViewModel.Output {
        return object: SettingsViewModel.Output {
            override fun getResetData(): LiveData<Unit> {
                return resetLiveData
            }
        }
    }

    override fun getViewEvent(): SettingsViewModel.ViewEvent {
        return object: SettingsViewModel.ViewEvent {
            private fun setupInitialStorageService(): DataSourcePresentationModel {
                val start = settingsInteractor.getSelectedDataSource()
                return if (start == null) {
                    val dataSource = DataSourceModel.create(DataSource.M3U)
                    settingsInteractor.updateSelectedDataSource(dataSource)
                    DataSourcePresentationModel.create(dataSource)
                } else {
                    DataSourcePresentationModel.create(start)
                }
            }

            override fun startScreen() {
                dataSourceLiveData.value =setupInitialStorageService()
            }

            override fun openPrivacyPolicy() {
                webLiveData.value = settingsInteractor.getPrivacyPolicy()
            }

            override fun openSourceSelectionDialog() {
                openSourceSelectionDialogLiveData.value = dataSourceLiveData.value
            }

            override fun updateSource(dataSource: DataSourcePresentationModel) {
                if (dataSourceLiveData.value == dataSource)
                    return
                settingsInteractor.updateSelectedDataSource(DataSourceModel(dataSource.id, dataSource.label, dataSource.url, dataSource.isEditable))
                dataSourceLiveData.value = dataSource
                resetLiveData.value = Unit
            }
        }

    }
}