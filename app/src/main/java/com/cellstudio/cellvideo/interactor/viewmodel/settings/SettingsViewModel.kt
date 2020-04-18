package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface SettingsViewModel : ViewModel {
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData
    fun getOutput(): Output

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
        fun openSourceSelectionDialog()
        fun openPrivacyPolicy()
        fun updateSource(dataSource: DataSourcePresentationModel)
    }

    interface ViewData : ViewModel.ViewData {
        fun getDataSource(): LiveData<String>
        fun getOpenSourceSelectionDialog(): LiveData<DataSourcePresentationModel>
        fun getOpenWebView(): LiveData<String>
    }

    interface Output : ViewModel.Output {
        fun getResetData(): LiveData<Unit>
    }

    interface Input : ViewModel.Input
}
