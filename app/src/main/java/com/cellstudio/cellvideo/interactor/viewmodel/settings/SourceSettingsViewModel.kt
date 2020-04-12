package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface SourceSettingsViewModel : ViewModel {
    fun setInput(input: SourceSettingsViewModel.Input)
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData
    fun getOutput(): Output

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
        fun addSource(model: DataSourcePresentationModel)
        fun removeSource(id: String)
    }

    interface ViewData : ViewModel.ViewData {
        fun getLiveSources(): LiveData<Pair<List<DataSourcePresentationModel>, DataSourcePresentationModel?>>
        fun getRemovedSource(): LiveData<String>
        fun getAddedSource(): LiveData<DataSourcePresentationModel>
    }

    interface Output : ViewModel.Output

    interface Input : ViewModel.Input {
        val initialSource: DataSourcePresentationModel?
    }
}
