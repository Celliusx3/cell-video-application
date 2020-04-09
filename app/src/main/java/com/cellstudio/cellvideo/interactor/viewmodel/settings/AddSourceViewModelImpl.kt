package com.cellstudio.cellvideo.interactor.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.DataSourcePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class AddSourceViewModelImpl @Inject constructor(scheduler: SchedulerProvider, private val settingsInteractor: SettingsInteractor): BaseViewModel(scheduler), AddSourceViewModel {
    private val labelNameLiveData = MutableLiveData<String>()
    private val sourceUrlLiveData = MutableLiveData<String>()
    private val btnEnabledLiveData: MediatorLiveData<Boolean> = MediatorLiveData()
    private val showErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isSourceRealLiveData: MutableLiveData<DataSourcePresentationModel> = MutableLiveData()

    private lateinit var input: AddSourceViewModel.Input

    init {
        btnEnabledLiveData.addSource(sourceUrlLiveData) { btnEnabledLiveData.value = !it.isNullOrEmpty() && !labelNameLiveData.value.isNullOrEmpty() }
        btnEnabledLiveData.addSource(labelNameLiveData) { btnEnabledLiveData.value = !it.isNullOrEmpty() && !sourceUrlLiveData.value.isNullOrEmpty() }
    }

    override fun getViewData(): AddSourceViewModel.ViewData {
        return object: AddSourceViewModel.ViewData {
            override fun getSubmitButtonEnabled(): LiveData<Boolean> = btnEnabledLiveData
            override val loading: LiveData<Boolean> = loadingLiveData
            override fun getShowError(): LiveData<Boolean> = showErrorLiveData
            override fun getIsSourceDataReal(): LiveData<DataSourcePresentationModel> = isSourceRealLiveData
        }
    }

    override fun getOutput(): AddSourceViewModel.Output {
        return object: AddSourceViewModel.Output {}
    }

    override fun getViewEvent(): AddSourceViewModel.ViewEvent {
        return object: AddSourceViewModel.ViewEvent {
            override val labelName: MutableLiveData<String> = labelNameLiveData
            override val sourceUrl: MutableLiveData<String> = sourceUrlLiveData
            override fun onSubmitButtonClicked() {
                sourceUrlLiveData.value?.let {
                    showErrorLiveData.value = false
                    loadingLiveData.value = true
                    compositeDisposable.add(settingsInteractor.validateDateSource(it)
                        .compose(applySchedulers())
                        .doFinally { loadingLiveData.value = false }
                        .subscribe({validSource ->
                            if (validSource) {
                                isSourceRealLiveData.value = DataSourcePresentationModel(UUID.randomUUID().toString(), labelNameLiveData.value?:"", it, true)
                            } else {
                                showErrorLiveData.value = true
                            }
                        }, {
                            it.printStackTrace()
                            showErrorLiveData.value = true }))
                }
            }
        }
    }
}