package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.cellstudio.cellvideo.constants.SharedPrefConstants
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModelImpl @Inject constructor(scheduler: SchedulerProvider,
                                                private val storageService: StorageService
): BaseViewModel(scheduler), SettingsViewModel {
    private val dataSourceLiveData: MutableLiveData<DataSource> = MutableLiveData()
    private val openSourceSelectionDialogLiveData: MutableLiveData<DataSource> = MutableLiveData()
    private val resetLiveData: MutableLiveData<Unit> = MutableLiveData()


    override fun getViewData(): SettingsViewModel.ViewData {
        return object: SettingsViewModel.ViewData {
            override fun getDataSource(): LiveData<String> = Transformations.map (dataSourceLiveData) { it.text }
            override fun getOpenSourceSelectionDialog(): LiveData<DataSource> = openSourceSelectionDialogLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
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
            private fun setupInitialStorageService(): DataSource {
                val start = storageService.getString(SharedPrefConstants.start)
                return if (start.isNullOrEmpty()) {
                    storageService.setString(SharedPrefConstants.start, DataSource.M3U.name)
                    DataSource.M3U
                } else {
                    DataSource.valueOf(start)
                }
            }

            override fun startScreen() {
                dataSourceLiveData.value =setupInitialStorageService()
            }

            override fun openSourceSelectionDialog() {
                openSourceSelectionDialogLiveData.value = dataSourceLiveData.value
            }

            override fun updateSource(dataSource: DataSource) {
                if (dataSourceLiveData.value == dataSource)
                    return
                storageService.setString(SharedPrefConstants.start, dataSource.name)
                dataSourceLiveData.value = dataSource
                resetLiveData.value = Unit
            }
        }

    }
}