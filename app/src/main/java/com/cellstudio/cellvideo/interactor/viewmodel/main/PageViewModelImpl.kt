package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cellstudio.cellvideo.domain.interactor.page.PageInteractor
import com.cellstudio.cellvideo.interactor.model.presentationmodel.FilterPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.interactor.viewmodel.base.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class PageViewModelImpl @Inject constructor(scheduler: SchedulerProvider, private val pageInteractor: PageInteractor): BaseViewModel(scheduler),
    PageViewModel {
    private lateinit var input: PageViewModel.Input
    private var page: Int ?= 0

    private val liveSources: MutableLiveData<List<LiveSourcePresentationModel>> = MutableLiveData()
    private val isGridView: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {value = false}
    private val openFilterSelectionDialog: MutableLiveData<Pair<List<FilterPresentationModel>, FilterPresentationModel?>> = MutableLiveData()
    private val selected: MutableLiveData<FilterPresentationModel> = MutableLiveData()

    override fun setInput(input: PageViewModel.Input) {
        this.input = input
        selected.value = input.filter?.models?.get(0)
    }

    override fun getViewEvent(): PageViewModel.ViewEvent {
        return object: PageViewModel.ViewEvent {
            override fun onLoadMore() {
                test()
            }

            private fun callLiveSourceAPI(page: Int): Observable<List<LiveSourcePresentationModel>> {
                return pageInteractor.getLiveSources(page, 50, input.data)
                    .map {it.map { LiveSourcePresentationModel.create(it) }}
                    .compose(applySchedulers())
            }

            private fun test() {
                loadingLiveData.value = true
                page?.let {value ->
                    compositeDisposable.add(callLiveSourceAPI(value)
                        .subscribe {
                            loadingLiveData.value = false
                            liveSources.value = it
                            page =  value + 1
                        }
                    )
                }
            }

            override fun startScreen() {
                test()
            }

            override fun onViewTypeClicked() {
                isGridView.value = isGridView.value?.not()
            }

            override fun onFilterButtonClicked() {
//                openFilterSelectionDialog.value = Pair(input.filter?.models?: listOf(), selected.value)
            }

            override fun onFilterSelected(filter: FilterPresentationModel) {
//                selected.value = filter
//                loadingLiveData.value = true
//                compositeDisposable.add(callLiveSourceAPI()
//                    .subscribe {
//                        loadingLiveData.value = false
//                        liveSources.value = it
//                    }
//                )
            }
        }
    }

    override fun getViewData(): PageViewModel.ViewData {
        return object: PageViewModel.ViewData {
            override fun getLiveSources(): LiveData<List<LiveSourcePresentationModel>> = liveSources
            override fun getIsGridView(): LiveData<Boolean> = isGridView
            override fun getOpenFilterDialog(): LiveData<Pair<List<FilterPresentationModel>, FilterPresentationModel?>> = openFilterSelectionDialog
            override val loading: LiveData<Boolean> = loadingLiveData
            override fun getSelectedFilter(): LiveData<FilterPresentationModel> = selected
        }
    }
}