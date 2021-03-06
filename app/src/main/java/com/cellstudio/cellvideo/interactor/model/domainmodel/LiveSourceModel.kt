package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuLiveSourceDetails
import com.cellstudio.cellvideo.data.entities.m3u.general.M3UItem

//data class LiveSourceModel(
//    val id: String,
//    val name: String,
//    val videoUrl: Map<String, String>?
//) {
//    companion object {
//        fun create(model: EYunZhuLiveSourceDetails): LiveSourceModel {
//            val map = hashMapOf<String, String>()
//            model.data.forEach { map[it.name] = it.url }
//            return LiveSourceModel(
//                model.id.toString(),
//                model.name,
//                map
//            )
//        }
//
//        fun create(model: List<M3UItem>): LiveSourceModel {
//            val map = hashMapOf<String, String>()
//            model.forEach { map[it.name] = it.url }
//            return LiveSourceModel(
//                "",
//                "",
//                map
//            )
//        }
//    }
//}

data class LiveSourceModel(
    val id: String,
    val name: String,
    val image: String,
    val url: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val type: ContentType
) {
    enum class ContentType {
        VIDEO,
        LIVESOURCE
    }

    companion object {
        fun create(model: EYunZhuLiveSourceDetails): LiveSourceModel {
            return LiveSourceModel(
                "",
                "",
                "",
                "",
                0,
                0,
                ContentType.LIVESOURCE
            )
        }

        fun create(model: M3UItem): LiveSourceModel {
            return LiveSourceModel(
                model.name,
                model.name,
                model.metadata["tvg-logo"]?: "",
                model.url,
                379,
                268,
                ContentType.LIVESOURCE
            )
        }

        fun create(id: String, name: String, contentType: ContentType): LiveSourceModel {
            return LiveSourceModel(
                id,
                name,
                "",
                "",
                0,
                0,
                contentType
            )
        }
    }
}