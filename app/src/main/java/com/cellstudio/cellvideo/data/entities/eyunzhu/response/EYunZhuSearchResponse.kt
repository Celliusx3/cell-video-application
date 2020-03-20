package com.cellstudio.cellvideo.data.entities.eyunzhu.response

import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuDetails
import com.google.gson.annotations.SerializedName

data class EYunZhuSearchResponse(
    val code: Int,
    val msg: String,
    val data: EYunZhuSearchData
) {
    data class EYunZhuSearchData(
        val total: Int,
        @SerializedName("per_page")
        val count: Int,
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        val data: List<EYunZhuDetails>
    )
}