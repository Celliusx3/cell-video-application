package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagePresentationModel(
    val id: Int,
    val name: String,
    val icon: Int,
    val viewType: PageViewTypePresentationModel
): Parcelable {
    companion object {
        fun create(model: PageModel): PagePresentationModel {
            return PagePresentationModel(
                model.id,
                model.name,
                model.icon,
                PageViewTypePresentationModel.VIEW_TYPE_TEXT
            )
        }
    }
}

enum class PageViewTypePresentationModel {
    VIEW_TYPE_TEXT
}