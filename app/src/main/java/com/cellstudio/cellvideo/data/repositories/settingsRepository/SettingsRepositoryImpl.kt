package com.cellstudio.cellvideo.data.repositories.settingsRepository

import com.cellstudio.cellvideo.constants.SharedPrefConstants
import com.cellstudio.cellvideo.data.db.AppDatabase
import com.cellstudio.cellvideo.data.db.dao.SourceDao
import com.cellstudio.cellvideo.data.db.mapper.RoomEntityMapper
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.data.environment.Environment
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.domain.repository.SettingsRepository
import io.reactivex.Observable

class SettingsRepositoryImpl(private val storageService: StorageService,
                             private val database: AppDatabase,
                             private val environment: Environment): SettingsRepository {
    private fun getSourceDao(): SourceDao {
        return database.SourceDao()
    }

    override fun addNewSource(dataSource: DataSource): Observable<Long> {
        return getSourceDao().insertSource(RoomEntityMapper.create(dataSource)).toObservable()
    }

    override fun removeSource(id: String): Observable<Int> {
        return getSourceDao().deleteSource(id).toObservable()
    }

    override fun getSources(): Observable<List<DataSource>> {
        return getSourceDao().getSources()
            .map { it.map { source -> RoomEntityMapper.create(source) } }
            .toObservable()
    }

    override fun getSelectedSource(): DataSource? {
        return storageService.getSelectedDataSource(SharedPrefConstants.SELECTED_DATA_SOURCE)
    }

    override fun updateSelectedSource(selectedSource: DataSource) {
        storageService.setSelectedDataSource(SharedPrefConstants.SELECTED_DATA_SOURCE, selectedSource)
    }

    override fun getPrivacyPolicy(): String {
        return environment.getPrivacyUrl()
    }
}