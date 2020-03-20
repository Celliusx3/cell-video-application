package com.cellstudio.cellvideo.data.repositories.eyunzhurepository

import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

interface EYunZhuRepository {
    fun search(keyword: String, count: Int, page: Int): Observable<List<VideoModel>>
    fun getDetails(id: Int): Observable<VideoModel>
    fun getLiveSource(): Observable<List<LiveSourceModel>>
    fun getPages(): List<PageModel>
}