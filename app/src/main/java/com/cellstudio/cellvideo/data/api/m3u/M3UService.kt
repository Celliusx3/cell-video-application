package com.cellstudio.cellvideo.data.api.m3u

import com.cellstudio.cellvideo.data.entities.m3u.general.M3UItem
import io.reactivex.Observable
import retrofit2.http.GET

interface M3UService {
    @GET(M3UApiRoutes.API_ENDPOINT)
    fun getLiveSources(): Observable<List<M3UItem>>
}