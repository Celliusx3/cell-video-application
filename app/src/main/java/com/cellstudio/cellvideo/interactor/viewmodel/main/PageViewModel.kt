package com.cellstudio.cellvideo.interactor.viewmodel.main

import androidx.lifecycle.LiveData
import com.cellstudio.cellvideo.interactor.model.presentationmodel.FilterListPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.FilterPresentationModel
import com.cellstudio.cellvideo.interactor.model.presentationmodel.LiveSourcePresentationModel
import com.cellstudio.cellvideo.interactor.viewmodel.base.ViewModel

interface PageViewModel : ViewModel {
    //    val output: Observable<Output>
    fun setInput(input: Input)
    fun getViewEvent(): ViewEvent
    fun getViewData(): ViewData

    interface ViewEvent : ViewModel.ViewEvent {
        fun startScreen()
        fun onViewTypeClicked()
        fun onFilterButtonClicked()
        fun onFilterSelected(filter: FilterPresentationModel)
        fun onLoadMore()
    }

    interface ViewData : ViewModel.ViewData {
        fun getLiveSources(): LiveData<List<LiveSourcePresentationModel>>
        fun getIsGridView(): LiveData<Boolean>
        fun getOpenFilterDialog(): LiveData<Pair<List<FilterPresentationModel>, FilterPresentationModel?>>
        fun getSelectedFilter(): LiveData<FilterPresentationModel>
    }

    interface Output : ViewModel.Output

    interface Input : ViewModel.Input {
        val filter: FilterListPresentationModel?
        val data: Map<String, String>
    }

    enum class ViewType {
        GRID,
        TEXT
    }
}
