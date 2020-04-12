package com.cellstudio.cellvideo.data.db.mapper

import com.cellstudio.cellvideo.data.db.entity.SourceEntity
import com.cellstudio.cellvideo.data.entities.general.DataSource

class RoomEntityMapper {

    companion object {
        fun create (model: SourceEntity): DataSource {
            return DataSource(model.id,
                model.label,
                model.url,
                model.isEditable)
        }

        fun create (model: DataSource): SourceEntity {
            return SourceEntity(model.id,
                model.label,
                model.url,
                model.isEditable)
        }
    }
}