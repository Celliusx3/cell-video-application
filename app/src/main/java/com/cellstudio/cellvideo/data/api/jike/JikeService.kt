package com.cellstudio.cellvideo.data.api.jike

import com.cellstudio.cellvideo.data.entities.jike.JikeVideo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JikeService {
    @GET(JikeApiRoutes.API_ENDPOINT)
    fun getVideosBanner(@Query("q") queryParameter: String, @Query("count") count: Int): Observable<Map<String, JikeVideo>>

    @GET(JikeApiRoutes.API_ENDPOINT)
    fun getVideoDetails(@Query("q") queryParameter: String = "name", @Query("n") name: String): Observable<HashMap<String, Any>>

    @GET(JikeApiRoutes.API_ENDPOINT)
    fun getVideosList(@Query("t") type: String, @Query("count") count: Int, @Query("q") queryParameter: String = "type"): Observable<HashMap<String, JikeVideo>>
}