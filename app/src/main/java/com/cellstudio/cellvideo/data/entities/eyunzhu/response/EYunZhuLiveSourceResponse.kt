package com.cellstudio.cellvideo.data.entities.eyunzhu.response

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuLiveSourceDetails

data class EYunZhuLiveSourceResponse(
    val code: Int,
    val msg: String,
    val data: List<EYunZhuLiveSourceDetails>
)