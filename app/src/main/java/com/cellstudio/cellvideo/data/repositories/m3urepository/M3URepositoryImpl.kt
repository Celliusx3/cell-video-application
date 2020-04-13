package com.cellstudio.cellvideo.data.repositories.m3urepository

import com.cellstudio.cellvideo.constants.Constants
import com.cellstudio.cellvideo.data.api.m3u.M3UService
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.entities.m3u.M3UPage
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

class M3URepositoryImpl(private val httpClient: HttpClient): DataSourceRepository {
    override fun search(id: String) {}

    override fun getPages(): List<PageModel> {
        return listOf(M3UPage.CountryPage, M3UPage.CategoryPage, M3UPage.LanguagePage).map { PageModel.create(it) }
    }

    private fun getLiveSourcesByCountries(id: String): Observable<List<LiveSourceModel>> {
        return getApiService().getLiveSourcesByCountries(id)
            .map { it.map {LiveSourceModel.create(it)}}
    }
    private fun getLiveSourcesByLanguages(id: String): Observable<List<LiveSourceModel>> {
        return getApiService().getLiveSourcesByLanguages(id)
            .map { it.map {LiveSourceModel.create(it)}}
    }

    private fun getLiveSourcesByCategories(id: String): Observable<List<LiveSourceModel>> {
        return getApiService().getLiveSourcesByCategories(id)
            .map { it.map {LiveSourceModel.create(it)}}
    }

    override fun validateDataSource(url: String): Observable<Boolean> {
        return getApiService().getLiveSources(url)
            .map { it.isNotEmpty() }
    }

    override fun getLiveSource(map: Map<String, Any>): Observable<List<LiveSourceModel>> {
        val type = map[Constants.type] as String?
        val id = map[Constants.id] as String?

        if (type.isNullOrEmpty()) {
            return Observable.error(Exception("Type is not provided"))
        }

        if (id.isNullOrEmpty()) {
            return Observable.error(Exception("Id is not provided"))
        }

        return when (type) {
            Constants.Category -> getLiveSourcesByCategories(id)
            Constants.Country -> getLiveSourcesByCountries(id)
            else -> getLiveSourcesByLanguages(id)
        }

    }

    private fun getApiService(): M3UService {
        return httpClient.getM3UApiService()
    }

    override fun getDetails(id: String): Observable<VideoModel> {
        return Observable.error(Exception("Fuck!"))
    }
}