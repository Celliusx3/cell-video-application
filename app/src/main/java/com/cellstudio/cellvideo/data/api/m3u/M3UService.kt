package com.cellstudio.cellvideo.data.api.m3u

import com.cellstudio.cellvideo.data.entities.m3u.general.M3UItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface M3UService {
    @GET(M3UApiRoutes.GET_PLAYLISTS_BY_CATEGORY)
    fun getLiveSourcesByCategories(@Path("id") id: String): Observable<List<M3UItem>>

    @GET(M3UApiRoutes.GET_PLAYLISTS_BY_COUNTRY)
    fun getLiveSourcesByCountries(@Path("id") id: String): Observable<List<M3UItem>>

    @GET(M3UApiRoutes.GET_PLAYLISTS_BY_LANGUAGE)
    fun getLiveSourcesByLanguages(@Path("id") id: String): Observable<List<M3UItem>>

    @GET
    fun getLiveSources(@Url url: String): Observable<List<M3UItem>>
}