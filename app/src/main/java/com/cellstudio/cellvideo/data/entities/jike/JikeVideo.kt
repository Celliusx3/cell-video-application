package com.cellstudio.cellvideo.data.entities.jike

import com.google.gson.annotations.SerializedName

data class JikeVideo(
    @SerializedName("mid")
    val id: String,
    @SerializedName("mname")
    val name: String,
    @SerializedName("mimgurl")
    val imageUrl: String?,
    @SerializedName("mscore")
    val score: String,
    @SerializedName("mdirector")
    val director: String,
    @SerializedName("mstar")
    val star: String,
    @SerializedName("mtype")
    val type: String,
    @SerializedName("marea")
    val area: String,
    @SerializedName("myear")
    val year: String,
    @SerializedName("msumary")
    val summary: String,
    @SerializedName("mplayurl")
    val videoUrl: String
)