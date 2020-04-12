package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.domain.interactor.page.PageInteractor
import com.cellstudio.cellvideo.domain.interactor.page.PageInteractorImpl
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractor
import com.cellstudio.cellvideo.domain.interactor.settings.SettingsInteractorImpl
import com.cellstudio.cellvideo.domain.interactor.splash.SplashInteractor
import com.cellstudio.cellvideo.domain.interactor.splash.SplashInteractorImpl
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.domain.repository.SettingsRepository
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

    @Provides
    @Singleton
    fun providePageInteractor(dataSource: DataSourceRepository): PageInteractor {
        return PageInteractorImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideSettingsInteractor(settingsRepository: SettingsRepository, dataSourceRepository: DataSourceRepository): SettingsInteractor {
        return SettingsInteractorImpl(settingsRepository, dataSourceRepository)
    }
}