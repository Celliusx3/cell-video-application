package com.cellstudio.cellvideo.data.repositories.m3urepository

import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import io.reactivex.Observable

interface M3URepository {
    fun getPages(): List<PageModel>
    fun getLiveSource(name: String): Observable<List<LiveSourceModel>>
}