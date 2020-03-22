package com.cellstudio.cellvideo.domain.repository

import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import io.reactivex.Observable

interface DataSourceRepository {
    fun getPages(): List<PageModel>
    fun getLiveSource(name: String): Observable<List<LiveSourceModel>>

}