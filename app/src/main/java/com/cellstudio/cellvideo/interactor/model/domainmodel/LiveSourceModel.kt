package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuLiveSourceDetails

data class LiveSourceModel(
    val id: String,
    val name: String,
    val videoUrl: Map<String, String>?
) {
    companion object {
        fun create(model: EYunZhuLiveSourceDetails): LiveSourceModel {
            val map = hashMapOf<String, String>()
            model.data.forEach { map[it.name] = it.url }
            return LiveSourceModel(
                model.id.toString(),
                model.name,
                map
            )
        }
    }
}