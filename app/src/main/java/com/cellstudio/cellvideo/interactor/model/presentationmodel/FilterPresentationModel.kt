package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.FilterListModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.FilterModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterListPresentationModel(
    val type: String,
    val models: List<FilterPresentationModel>):Parcelable {
    companion object {
        fun create(model: FilterListModel): FilterListPresentationModel {
            return FilterListPresentationModel(
                model.type,
                model.models.map { FilterPresentationModel.create(it) }
            )
        }
    }
}

@Parcelize
data class FilterPresentationModel(
    val id: String,
    val displayText: String
): Parcelable, SelectionModel {

    override fun getText(): String {
        return displayText
    }

    companion object {
        fun create(model: FilterModel): FilterPresentationModel {
            return FilterPresentationModel(
                model.id,
                model.displayText
            )
        }
    }
}