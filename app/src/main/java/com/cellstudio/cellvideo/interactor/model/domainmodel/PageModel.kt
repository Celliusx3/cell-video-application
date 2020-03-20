package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuPage
import com.cellstudio.cellvideo.data.entities.jike.JikePage

data class PageModel(
    val id: Int,
    val name: String,
    val icon: Int
) {
    companion object {
        fun create(model: JikePage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon
            )
        }

        fun create(model: EYunZhuPage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon
            )
        }
    }
}