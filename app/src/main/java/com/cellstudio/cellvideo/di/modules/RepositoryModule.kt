package com.cellstudio.cellvideo.di.modules

import android.util.Log
import com.cellstudio.cellvideo.constants.SharedPrefConstants
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepositoryImpl
import com.cellstudio.cellvideo.data.repositories.jikerepository.JikeRepositoryImpl
import com.cellstudio.cellvideo.data.repositories.m3urepository.M3URepository
import com.cellstudio.cellvideo.data.repositories.m3urepository.M3URepositoryImpl
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDataSourceRepository(storage: StorageService, httpClient: HttpClient): DataSourceRepository {
        Log.d("GGTest", storage.getString(SharedPrefConstants.start))
        return when (storage.getString(SharedPrefConstants.start)) {
            DataSource.EYUNZHU.name -> EYunZhuRepositoryImpl(httpClient)
            DataSource.JIKE.name -> JikeRepositoryImpl(httpClient)
            else -> M3URepositoryImpl(httpClient)
        }
    }


//    @Provides
//    @Singleton
//    fun provideJikeRepository(httpClient: HttpClient): JikeRepository {
//        return JikeRepositoryImpl(httpClient)
//    }

//    @Provides
//    @Singleton
//    fun provideConfigRepository(): ConfigRepository {
//        return ConfigRepositoryImpl()
//    }

    @Provides
    @Singleton
    fun provideEYunZhuRepository(httpClient: HttpClient): EYunZhuRepository {
        return EYunZhuRepositoryImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideM3URepository(httpClient: HttpClient): M3URepository {
        return M3URepositoryImpl(httpClient)
    }
}