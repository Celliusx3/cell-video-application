package com.cellstudio.cellvideo.interactor.model.presentationmodel

import android.os.Parcelable
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoPresentationModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val imageHeight: Int,
    val imageWidth: Int,
    val score: String,
    val director: String,
    val star: String,
    val type: String,
    val area: String,
    val year: String,
    val summary: String,
    val videoUrl: Map<String, String>?,
    val viewType: VideoListViewType
): Parcelable {
    companion object {
        fun create(model: VideoModel): VideoPresentationModel {
            return VideoPresentationModel(
                model.id,
                model.name,
                model.imageUrl ?: "",
                381,
                270,
                model.score,
                model.director,
                model.star,
                model.type,
                model.area,
                model.year,
                model.summary,
                model.videoUrl,
                VideoListViewType.VIEW_TYPE_LIST
            )
        }

        fun create(title: String): VideoPresentationModel {
            return VideoPresentationModel(
                title,
                title,
                "",
                381,
                270,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                VideoListViewType.VIEW_TYPE_LIST
            )
        }
    }
}