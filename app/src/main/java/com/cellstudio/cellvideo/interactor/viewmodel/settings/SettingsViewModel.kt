package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface SettingsViewModel : ViewModel {
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData
    fun getOutput(): Output

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
        fun openSourceSelectionDialog()
        fun updateSource(dataSource: DataSource)
    }

    interface ViewData : ViewModel.ViewData {
        fun getDataSource(): LiveData<String>
        fun getOpenSourceSelectionDialog(): LiveData<DataSource>
    }

    interface Output : ViewModel.Output {
        fun getResetData(): LiveData<Unit>
    }

    interface Input : ViewModel.Input
}
