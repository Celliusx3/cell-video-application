package com.cellstudio.cellvideo.data.repositories.m3urepository

import com.cellstudio.cellvideo.data.api.m3u.M3UService
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.entities.m3u.M3UPage
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import io.reactivex.Observable

class M3URepositoryImpl(private val httpClient: HttpClient): DataSourceRepository, M3URepository {
    override fun getLiveSource(name: String): Observable<List<LiveSourceModel>> {
        return getApiService().getLiveSources()
            .map { listOf(LiveSourceModel.create(it)) }
    }

    override fun getPages(): List<PageModel> {
        return listOf(M3UPage.HomePage).map { PageModel.create(it) }
    }

    private fun getApiService(): M3UService {
        return httpClient.getM3UApiService()
    }
}