package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuPage
import com.cellstudio.cellvideo.data.entities.jike.JikePage
import com.cellstudio.cellvideo.data.entities.m3u.M3UPage

data class PageModel(
    val id: Int,
    val name: String,
    val icon: Int,
    val viewType: PageViewTypeModel
) {
    companion object {
        fun create(model: JikePage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon,
                PageViewTypeModel.VIEW_TYPE_TEXT
            )
        }

        fun create(model: EYunZhuPage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon,
                PageViewTypeModel.VIEW_TYPE_TEXT
            )
        }

        fun create(model: M3UPage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon,
                PageViewTypeModel.VIEW_TYPE_TEXT
            )
        }
    }
}

enum class PageViewTypeModel {
    VIEW_TYPE_TEXT
}