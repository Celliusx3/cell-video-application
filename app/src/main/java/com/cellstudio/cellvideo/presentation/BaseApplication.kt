package com.cellstudio.cellvideo.presentation

import android.app.Application
import com.cellstudio.cellvideo.di.components.ApplicationComponent
import com.cellstudio.cellvideo.di.components.DaggerApplicationComponent
import com.cellstudio.cellvideo.di.modules.ApplicationModule
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject



class BaseApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build().inject(this)

    }

    companion object {
        // Singleton Instance
        private lateinit var singleton: BaseApplication

        fun getInstance() : BaseApplication { return singleton }
    }

}