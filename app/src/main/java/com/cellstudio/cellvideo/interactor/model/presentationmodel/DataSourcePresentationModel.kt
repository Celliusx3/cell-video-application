package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.DataSourceModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataSourcePresentationModel(
    val id: String,
    val label: String,
    val url: String,
    val isEditable: Boolean): Parcelable {
    companion object {
        fun create(model: DataSourceModel): DataSourcePresentationModel {
            return DataSourcePresentationModel(model.id, model.label, model.url, model.isEditable)
        }
    }
}