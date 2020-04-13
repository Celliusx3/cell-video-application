package com.cellstudio.cellvideo.data.base

import com.cellstudio.cellvideo.data.api.eyunzhu.EYunZhuService
import com.cellstudio.cellvideo.data.api.m3u.M3UService

interface HttpClient {
    fun getEYunZhuApiService(): EYunZhuService
    fun getM3UApiService(): M3UService
}