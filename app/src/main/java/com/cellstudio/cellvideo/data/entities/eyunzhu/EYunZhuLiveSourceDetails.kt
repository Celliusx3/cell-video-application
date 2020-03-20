package com.cellstudio.cellvideo.data.entities.eyunzhu

import com.google.gson.annotations.SerializedName

data class EYunZhuLiveSourceDetails(
    val id: Int,
    val name: String,
    val data: List<EYunZhuSingleLiveSourceDetails>
) {
    data class EYunZhuSingleLiveSourceDetails (
        val name: String,
        val url: String,
        @SerializedName("url_type")
        val urlType: String,
        @SerializedName("cross_domain")
        val crossDomain: Int,
        val https: Int
    )
}