package com.cellstudio.cellvideo.data.entities.eyunzhu.response

data class EYunZhuTopVideosResponse(
    val code: Int,
    val msg: String,
    val data: List<EYunZhuTopVideos>
) {
    data class EYunZhuTopVideos(
        val name: String,
        val data: Map<String, String>
    )
}