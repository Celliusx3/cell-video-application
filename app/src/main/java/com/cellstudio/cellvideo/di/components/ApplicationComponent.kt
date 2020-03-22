package com.cellstudio.cellvideo.di.components

import com.cellstudio.cellvideo.di.modules.ActivityBuildersModule
import com.cellstudio.cellvideo.di.modules.ApplicationModule
import com.cellstudio.cellvideo.di.modules.InteractorModule
import com.cellstudio.cellvideo.di.modules.RepositoryModule
import com.cellstudio.cellvideo.presentation.BaseApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ApplicationModule::class,
    RepositoryModule::class,
    InteractorModule::class,
    ActivityBuildersModule::class
])
interface ApplicationComponent {
    fun inject(baseApplication: BaseApplication)
}