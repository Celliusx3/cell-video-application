package com.cellstudio.cellvideo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Source")
class SourceEntity(
    @PrimaryKey val id: String,
    val label: String,
    val url: String,
    val isEditable: Boolean
)