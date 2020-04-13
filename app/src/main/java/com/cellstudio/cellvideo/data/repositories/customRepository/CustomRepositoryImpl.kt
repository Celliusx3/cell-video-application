package com.cellstudio.cellvideo.data.repositories.customRepository

import com.cellstudio.cellvideo.constants.Constants
import com.cellstudio.cellvideo.constants.SharedPrefConstants
import com.cellstudio.cellvideo.data.api.m3u.M3UService
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.entities.custom.CustomPage
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

class CustomRepositoryImpl(private val httpClient: HttpClient, private val storage: StorageService): DataSourceRepository {
    override fun getPages(): List<PageModel> {
        return listOf(CustomPage.HomePage).map { PageModel.create(it, hashMapOf(Pair(Constants.url
        , storage.getSelectedDataSource(SharedPrefConstants.SELECTED_DATA_SOURCE)?.url?:""))) }
    }

    override fun getDetails(id: String): Observable<VideoModel> {
        return Observable.error(Exception("Fuck!"))
    }

    override fun getLiveSource(map: Map<String, Any>): Observable<List<LiveSourceModel>> {
        val url = map[Constants.url] as String?
        if (url.isNullOrEmpty()) {
            return Observable.error(Exception("Url is not provided"))
        }

        return getApiService().getLiveSources(url)
            .map { it.map { LiveSourceModel.create(it)} }
    }

    override fun validateDataSource(url: String): Observable<Boolean> {
        return getApiService().getLiveSources(url)
            .map { it.isNotEmpty() }
    }

    override fun search(id: String) {}

    private fun getApiService(): M3UService {
        return httpClient.getM3UApiService()
    }
}