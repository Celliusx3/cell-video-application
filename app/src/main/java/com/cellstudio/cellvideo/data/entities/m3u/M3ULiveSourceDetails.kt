package com.cellstudio.cellvideo.data.entities.m3u

data class M3ULiveSourceDetails(
    val id: String,
    val name: String,
    val language: String,
    val logo: String,
    val country: String,
    val epgUrl: String,
    val group: String,
    val url: String
)