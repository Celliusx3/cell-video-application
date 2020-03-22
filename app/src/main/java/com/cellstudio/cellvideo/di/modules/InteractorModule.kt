package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.domain.interactor.splash.SplashInteractor
import com.cellstudio.cellvideo.domain.interactor.splash.SplashInteractorImpl
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideSplashInteractor(dataSource: DataSourceRepository): SplashInteractor {
        return SplashInteractorImpl(dataSource)
    }
}