package com.cellstudio.cellvideo.data.repositories.jikerepository

import com.cellstudio.cellvideo.data.entities.jike.JikeVideo
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

interface JikeRepository {
    fun getVideosBanner(name: String, count: Int): Observable<List<VideoModel>>
    fun getVideoDetails(name: String): Observable<Map<String, JikeVideo>>
    fun getVideosList(type: String, count: Int): Observable<List<VideoModel>>

    fun getPages(): List<PageModel>
    fun getJikeVideoTypes(): List<String>
}