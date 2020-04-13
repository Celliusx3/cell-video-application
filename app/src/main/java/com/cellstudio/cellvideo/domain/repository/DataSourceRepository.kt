package com.cellstudio.cellvideo.domain.repository

import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

interface DataSourceRepository {
    fun getPages(): List<PageModel>
    fun getLiveSource(map: Map<String, Any>): Observable<List<LiveSourceModel>>
    fun search(id: String)
    fun validateDataSource(url: String): Observable<Boolean>
    fun getDetails(id: String): Observable<VideoModel>

}