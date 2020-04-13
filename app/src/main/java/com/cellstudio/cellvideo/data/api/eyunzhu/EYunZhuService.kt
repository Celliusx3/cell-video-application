package com.cellstudio.cellvideo.data.api.eyunzhu

import com.cellstudio.cellvideo.data.entities.eyunzhu.response.EYunZhuDetailsResponse
import com.cellstudio.cellvideo.data.entities.eyunzhu.response.EYunZhuLiveSourceResponse
import com.cellstudio.cellvideo.data.entities.eyunzhu.response.EYunZhuSearchResponse
import com.cellstudio.cellvideo.data.entities.eyunzhu.response.EYunZhuTopVideosResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EYunZhuService {
    @GET(EYunZhuApiRoutes.SEARCH_ENDPOINT)
    fun search(@Query("kw") keyword: String, @Query("per_page") count: Int, @Query("page") page: Int): Observable<EYunZhuSearchResponse>

    @GET(EYunZhuApiRoutes.DETAILS_ENDPOINT)
    fun getDetails(@Query("vid")vid: String): Observable<EYunZhuDetailsResponse>

    @GET(EYunZhuApiRoutes.LIVE_ENDPOINT)
    fun getLiveSources(): Observable<EYunZhuLiveSourceResponse>

    @GET(EYunZhuApiRoutes.TOP_ENDPOINT)
    fun getTopVideos(): Observable<EYunZhuTopVideosResponse>
}