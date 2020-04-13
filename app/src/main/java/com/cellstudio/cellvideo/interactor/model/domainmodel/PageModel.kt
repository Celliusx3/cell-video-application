package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.custom.CustomPage
import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuPage
import com.cellstudio.cellvideo.data.entities.m3u.M3UPage

data class PageModel(
    val id: Int,
    val name: String,
    val icon: Int,
    val filter: FilterListModel?,
    val datas: Map<String, String>
) {
    companion object {
        fun create(model: CustomPage, datas: Map<String, String>): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon,
                null,
                datas
            )
        }

        fun create(model: EYunZhuPage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon,
                null,
                hashMapOf()
            )
        }

        fun create(model: M3UPage): PageModel {
            return PageModel(
                model.id,
                model.name,
                model.icon,
                FilterListModel.create(model.filter),
                model.datas
            )
        }
    }
}