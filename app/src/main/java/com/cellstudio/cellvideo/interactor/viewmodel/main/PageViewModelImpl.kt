package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.cellstudio.cellvideo.constants.Constants
import com.cellstudio.cellvideo.domain.interactor.page.PageInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.FilterPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.VideoPresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class PageViewModelImpl @Inject constructor(scheduler: SchedulerProvider, private val pageInteractor: PageInteractor): BaseViewModel(scheduler),
    PageViewModel {
    private lateinit var input: PageViewModel.Input
    private var page: Int ?= 0

    private val liveSources: MutableLiveData<Pair<List<LiveSourcePresentationModel>, Boolean>> = MutableLiveData()
    private val isGridView: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {value = false}
    private val openFilterSelectionDialog: MutableLiveData<Pair<List<FilterPresentationModel>, FilterPresentationModel?>> = MutableLiveData()
    private val selected: MutableLiveData<FilterPresentationModel> = MutableLiveData()
    private val details: MutableLiveData<VideoPresentationModel> = MutableLiveData()

    private val inputDataForAPI: MutableLiveData<Map<String, String>> = MutableLiveData()

    override fun setInput(input: PageViewModel.Input) {
        this.input = input
        inputDataForAPI.value = input.data
        selected.value = input.filter?.models?.find { it.id == input.data[Constants.id]}
    }

    override fun getViewEvent(): PageViewModel.ViewEvent {
        return object: PageViewModel.ViewEvent {
            override fun onLoadMore() {
                subscribeLiveSourceAPI(false, inputDataForAPI.value?: mapOf())
            }

            override fun onGetDetails(id: String) {
                loadingLiveData.value = true
                compositeDisposable.add(pageInteractor.getDetails(id)
                    .map {VideoPresentationModel.create(it)}
                    .compose(applySchedulers())
                    .subscribe {
                        loadingLiveData.value = false
                        details.value = it
                    })
            }

            private fun callLiveSourceAPI(page: Int, map: Map<String, String>): Observable<List<LiveSourcePresentationModel>> {
                return pageInteractor.getLiveSources(page, 50, map)
                    .map {it.map { LiveSourcePresentationModel.create(it) }}
                    .compose(applySchedulers())
            }

            private fun subscribeLiveSourceAPI(isNewStart: Boolean, map: Map<String, String>) {
                if (isNewStart) { page = 0 }
                loadingLiveData.value = true
                page?.let {value ->
                    compositeDisposable.add(callLiveSourceAPI(value, map)
                        .subscribe {
                            loadingLiveData.value = false
                            liveSources.value = Pair(it, isNewStart)
                            page =  value + 1
                        }
                    )
                }
            }

            override fun startScreen() {
                subscribeLiveSourceAPI(true, input.data)
            }

            override fun onViewTypeClicked() {
                isGridView.value = isGridView.value?.not()
            }

            override fun onFilterButtonClicked() {
                openFilterSelectionDialog.value = Pair(input.filter?.models?: listOf(), selected.value)
            }

            override fun onFilterSelected(filter: FilterPresentationModel) {
                selected.value = filter
                inputDataForAPI.value = mapOf(Pair(Constants.type, input.data["type"]?: ""), Pair(Constants.id, selected.value?.id?:""))
                subscribeLiveSourceAPI(true, inputDataForAPI.value?: mapOf())
            }
        }
    }

    override fun getViewData(): PageViewModel.ViewData {
        return object: PageViewModel.ViewData {
            override fun getSubtitle(): LiveData<String> = Transformations.map (selected) { it?.displayText?: "" }
            override fun getDetails(): LiveData<VideoPresentationModel>  = details
            override fun getLiveSources(): LiveData<Pair<List<LiveSourcePresentationModel>, Boolean>> = liveSources
            override fun getIsGridView(): LiveData<Boolean> = isGridView
            override fun getOpenFilterDialog(): LiveData<Pair<List<FilterPresentationModel>, FilterPresentationModel?>> = openFilterSelectionDialog
            override val loading: LiveData<Boolean> = loadingLiveData
            override fun getSelectedFilter(): LiveData<FilterPresentationModel> = selected
        }
    }
}