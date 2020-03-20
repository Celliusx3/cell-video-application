package com.cellstudio.cellvideo.data.repositories.m3urepository

import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

interface M3URepository {
    fun getChannelItems(name: String, count: Int): Observable<List<VideoModel>>
}