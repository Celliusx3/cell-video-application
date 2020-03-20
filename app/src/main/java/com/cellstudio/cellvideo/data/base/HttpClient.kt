package com.cellstudio.cellvideo.data.base

import com.cellstudio.cellvideo.data.api.eyunzhu.EYunZhuService
import com.cellstudio.cellvideo.data.api.jike.JikeService

interface HttpClient {
    fun getJikeApiService() : JikeService
    fun getEYunZhuApiService(): EYunZhuService
}