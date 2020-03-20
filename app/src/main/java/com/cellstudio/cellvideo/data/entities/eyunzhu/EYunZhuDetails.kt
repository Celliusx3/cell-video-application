package com.cellstudio.cellvideo.data.entities.eyunzhu

data class EYunZhuDetails(
    val vid: String?,
    val name: String,
    val label: String,
    val siteId: Int,
    val pic: String,
    val type: String,
    val updateTime: Number,
    val playUrl: Map<String, String>?
)