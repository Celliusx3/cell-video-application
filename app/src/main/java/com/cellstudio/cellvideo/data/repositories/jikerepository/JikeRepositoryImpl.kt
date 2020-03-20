package com.cellstudio.cellvideo.data.repositories.jikerepository

import com.cellstudio.cellvideo.constants.Constants
import com.cellstudio.cellvideo.data.api.jike.JikeService
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.entities.jike.JikePage
import com.cellstudio.cellvideo.data.entities.jike.JikeVideo
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

class JikeRepositoryImpl(private val httpClient: HttpClient): JikeRepository {
    override fun getVideosList(type: String, count: Int): Observable<List<VideoModel>> {
        return getApiService().getVideosList(type, count)
            .map { it.values.map { VideoModel.create( it ) }.toList() }
    }

    override fun getVideoDetails(name: String): Observable<Map<String, JikeVideo>> {
        return getApiService().getVideosBanner(name, 0)
    }

    override fun getVideosBanner(name: String, count: Int): Observable<List<VideoModel>> {
        return getApiService().getVideosBanner(name, count)
            .map { it.values.map { VideoModel.create( it ) }.toList() }
    }

    override fun getJikeVideoTypes(): List<String> {
        return listOf(Constants.movies, Constants.tvSeries, Constants.anime,  Constants.tvShows)
    }

    override fun getPages(): List<PageModel> {
        return listOf(JikePage.HomePage).map { PageModel.create(it) }
    }

    private fun getApiService(): JikeService {
        return httpClient.getJikeApiService()
    }
}