package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LiveSourcePresentationModel(
    val id: String,
    val name: String,
    val videoUrl: List<Pair<String, String>>?,
    val viewType: LiveSourceViewType
): Parcelable {
    companion object {
        fun create(model: LiveSourceModel): LiveSourcePresentationModel {
            return LiveSourcePresentationModel(
                model.id,
                model.name,
                model.videoUrl?.toList(),
                LiveSourceViewType.VIEW_TYPE_TEXT
            )
        }
    }
}

enum class LiveSourceViewType(val type: Int) {
    VIEW_TYPE_HEADER(99),
    VIEW_TYPE_GRID(100),
    VIEW_TYPE_LIST(101),
    VIEW_TYPE_RAIL(102),
    VIEW_TYPE_TEXT(103)
}