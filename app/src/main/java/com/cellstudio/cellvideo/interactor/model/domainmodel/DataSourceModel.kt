package com.cellstudio.cellvideo.interactor.model.domainmodel

import com.cellstudio.cellvideo.data.entities.general.DataSource

class DataSourceModel(
    val id: String,
    val label: String,
    val url: String,
    val isEditable: Boolean) {
    companion object {
        fun create(model: DataSource): DataSourceModel {
            return DataSourceModel(model.id, model.label, model.url, model.isEditable)
        }
    }
}