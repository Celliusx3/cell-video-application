package com.cellstudio.cellvideo.domain.interactor.page

import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

interface PageInteractor {
    fun getLiveSources(page: Int, count: Int, map: Map<String, Any>): Observable<List<LiveSourceModel>>
    fun getDetails(id: String): Observable<VideoModel>
}