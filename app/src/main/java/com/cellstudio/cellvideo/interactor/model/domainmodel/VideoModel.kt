package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuDetails
import com.cellstudio.cellvideo.data.entities.jike.JikeVideo

data class VideoModel(
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
    val videoUrl: Map<String, String>?
) {
    companion object {
        fun create(jike: JikeVideo): VideoModel {
            return VideoModel(
                jike.id,
                jike.name,
                jike.imageUrl ?: "",
                381,
                270,
                jike.score,
                jike.director,
                jike.star,
                jike.type,
                jike.area,
                jike.year,
                jike.summary,
                hashMapOf(Pair("", jike.videoUrl))
            )
        }

        fun create(model: EYunZhuDetails): VideoModel {
            return VideoModel(
                model.vid ?: "",
                model.name,
                model.pic ?: "",
                381,
                270,
                "0.0",
                "",
                 "",
                model.type,
                "",
                "",
                "",
                model.playUrl
            )
        }
    }
}