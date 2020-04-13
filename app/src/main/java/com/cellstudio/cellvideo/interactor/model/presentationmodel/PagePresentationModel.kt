package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagePresentationModel(
    val id: Int,
    val name: String,
    val icon: Int,
    val filter: FilterListPresentationModel?,
    val datas: Map<String, String>
): Parcelable {
    companion object {
        fun create(model: PageModel): PagePresentationModel {
            return PagePresentationModel(
                model.id,
                model.name,
                model.icon,
                model.filter?.let { FilterListPresentationModel.create(it) },
                model.datas
            )
        }
    }
}