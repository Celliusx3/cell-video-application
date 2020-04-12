package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.m3u.M3UFilter
import com.cellstudio.cellvideo.data.entities.m3u.M3UFilterList

data class FilterListModel(
    val type: String,
    val models: List<FilterModel>) {
    companion object {
        fun create(model: M3UFilterList): FilterListModel {
            return FilterListModel(
                model.type,
                model.filters.map { FilterModel.create(it) }
            )
        }
    }
}

data class FilterModel(
    val id: String,
    val displayText: String
) {
    companion object {
        fun create(model: M3UFilter): FilterModel {
            return FilterModel(
                model.id,
                model.displayText
            )
        }
    }
}