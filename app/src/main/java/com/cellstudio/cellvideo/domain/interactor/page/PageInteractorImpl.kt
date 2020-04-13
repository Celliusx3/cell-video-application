package com.cellstudio.cellvideo.domain.interactor.page

import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.LiveSourceModel
import com.cellstudio.cellvideo.interactor.model.domainmodel.VideoModel
import io.reactivex.Observable

class PageInteractorImpl(private val repository: DataSourceRepository): PageInteractor {
    private var models: List<LiveSourceModel> = listOf()

    override fun getLiveSources(
        page: Int,
        count: Int,
        map: Map<String, Any>
    ): Observable<List<LiveSourceModel>> {
        return repository.getLiveSource(map)
            .map {
                models = it
                return@map it.subList((page * count).coerceAtMost(models.size - 1), (page * count + count - 1).coerceAtMost(models.size - 1))
            }
    }

    override fun getDetails(id: String): Observable<VideoModel> {
        return repository.getDetails(id)
    }
}