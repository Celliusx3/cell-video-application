package com.cellstudio.cellvideo.data.repositories.eyunzhurepository

import com.cellstudio.cellvideo.data.api.eyunzhu.EYunZhuService
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.entities.eyunzhu.EYunZhuPage
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

class EYunZhuRepositoryImpl(private val httpClient: HttpClient): DataSourceRepository, EYunZhuRepository {
    private fun getApiService(): EYunZhuService {
        return httpClient.getEYunZhuApiService()
    }

    override fun search(keyword: String, count: Int, page: Int): Observable<List<VideoModel>> {
        return getApiService().search(keyword, count, page)
            .map { it.data.data.map { VideoModel.create( it ) }.toList() }
    }

    override fun getDetails(id: Int): Observable<VideoModel> {
        return getApiService().getDetails(id)
            .map {VideoModel.create( it.data ) }
    }

    override fun getLiveSource(): Observable<List<LiveSourceModel>> {
        return getApiService().getLiveSources()
            .map {it.data.map { LiveSourceModel.create(it)}}
    }

    override fun getPages(): List<PageModel> {
        return listOf(EYunZhuPage.HomePage).map { PageModel.create(it) }
    }

    override fun getLiveSource(name: String): Observable<List<LiveSourceModel>> {
        return getApiService().getLiveSources()
            .map {it.data.map { LiveSourceModel.create(it)}}
    }

}