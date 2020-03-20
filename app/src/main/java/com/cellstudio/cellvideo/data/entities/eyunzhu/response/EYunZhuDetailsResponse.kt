package com.cellstudio.cellvideo.data.entities.eyunzhu.response

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuDetails

data class EYunZhuDetailsResponse(
    val code: Int,
    val msg: String,
    val data: EYunZhuDetails
)