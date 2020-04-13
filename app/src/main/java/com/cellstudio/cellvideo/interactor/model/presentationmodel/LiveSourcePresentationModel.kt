package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import kotlinx.android.parcel.Parcelize

//@Parcelize
//data class LiveSourcePresentationModel(
//    val id: String,
//    val name: String,
//    val videoUrl: List<Pair<String, String>>?,
//    val viewType: LiveSourceViewType
//): Parcelable {
//    companion object {
//        fun create(model: LiveSourceModel): LiveSourcePresentationModel {
//            return LiveSourcePresentationModel(
//                model.id,
//                model.name,
//                listOf(Pair(model.url, model.url)),
//                LiveSourceViewType.VIEW_TYPE_TEXT
//            )
//        }
//    }
//}

@Parcelize
data class LiveSourcePresentationModel(
    val id: String,
    val name: String,
    val image: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val url: String,
    val type: ContentType
): Parcelable {

    enum class ContentType {
        VIDEO,
        LIVESOURCE
    }

    companion object {
        fun create(model: LiveSourceModel): LiveSourcePresentationModel {
            val contentType = if (model.type == LiveSourceModel.ContentType.LIVESOURCE) {
                ContentType.LIVESOURCE
            } else { ContentType.VIDEO }

            return LiveSourcePresentationModel(
                model.id,
                model.name,
                model.image,
                model.imageWidth,
                model.imageHeight,
                model.url,
                contentType
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