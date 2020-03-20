package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.repositories.configrepository.ConfigRepository
import com.cellstudio.cellvideo.data.repositories.configrepository.ConfigRepositoryImpl
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepository
import com.cellstudio.cellvideo.data.repositories.eyunzhurepository.EYunZhuRepositoryImpl
import com.cellstudio.cellvideo.data.repositories.jikerepository.JikeRepository
import com.cellstudio.cellvideo.data.repositories.jikerepository.JikeRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideJikeRepository(httpClient: HttpClient): JikeRepository {
        return JikeRepositoryImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideConfigRepository(): ConfigRepository {
        return ConfigRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideEYunZhuRepository(httpClient: HttpClient): EYunZhuRepository {
        return EYunZhuRepositoryImpl(httpClient)
    }
}