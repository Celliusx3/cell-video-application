package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface AddSourceViewModel : ViewModel {
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData
    fun getOutput(): Output

    interface ViewEvent : ViewModel.ViewEvent {
        val labelName: MutableLiveData<String>
        val sourceUrl: MutableLiveData<String>
        fun onSubmitButtonClicked()
    }

    interface ViewData : ViewModel.ViewData {
        fun getSubmitButtonEnabled(): LiveData<Boolean>
        fun getShowError(): LiveData<Boolean>
        fun getIsSourceDataReal(): LiveData<DataSourcePresentationModel>
    }

    interface Output : ViewModel.Output
    interface Input : ViewModel.Input
}
