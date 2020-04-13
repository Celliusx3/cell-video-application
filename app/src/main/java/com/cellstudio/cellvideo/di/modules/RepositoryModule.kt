package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.constants.SharedPrefConstants
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.db.AppDatabase
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.data.repositories.customRepository.CustomRepositoryImpl
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepositoryImpl
import com.cellstudio.cellvideo.data.repositories.m3urepository.M3URepositoryImpl
import com.cellstudio.cellvideo.data.repositories.settingsRepository.SettingsRepositoryImpl
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDataSourceRepository(storage: StorageService, httpClient: HttpClient): DataSourceRepository {
        if (storage.getSelectedDataSource(SharedPrefConstants.SELECTED_DATA_SOURCE)?.id == null) {
            return M3URepositoryImpl(httpClient)
        }

        return when (storage.getSelectedDataSource(SharedPrefConstants.SELECTED_DATA_SOURCE)?.id) {
            DataSource.EYUNZHU.id -> EYunZhuRepositoryImpl(httpClient)
            DataSource.M3U.id -> M3URepositoryImpl(httpClient)
            else -> CustomRepositoryImpl(httpClient, storage)
        }
    }

    @Provides
    @Singleton
    fun provideEYunZhuRepository(httpClient: HttpClient): EYunZhuRepository {
        return EYunZhuRepositoryImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(storage: StorageService, database: AppDatabase): SettingsRepository {
        return SettingsRepositoryImpl(storage, database)
    }

}