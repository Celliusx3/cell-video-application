package com.cellstudio.cellvideo.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.cellstudio.cellvideo.data.base.BaseHttpClient
import com.cellstudio.cellvideo.data.base.HttpClient
import com.cellstudio.cellvideo.data.db.AppDatabase
import com.cellstudio.cellvideo.data.environment.BaseEnvironment
import com.cellstudio.cellvideo.data.environment.Environment
import com.cellstudio.cellvideo.data.services.storage.SharedPrefsStorageService
import com.cellstudio.cellvideo.data.services.storage.StorageService
import com.cellstudio.cellvideo.interactor.scheduler.DefaultSchedulerProvider
import com.cellstudio.cellvideo.interactor.scheduler.SchedulerProvider
import com.cellstudio.cellvideo.presentation.BaseApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule(private val baseApplication: BaseApplication) {
    private var prefs: SharedPreferences? = null

    @Provides
    @Singleton
    fun application(): BaseApplication {
        return baseApplication
    }

    @Provides
    @Singleton
    fun provideApplicationContext(baseApplication: BaseApplication): Context {
        return baseApplication
    }

    @Provides
    @Singleton
    fun provideEnvironment(context: Context): Environment {
        return BaseEnvironment(context)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return BaseHttpClient()
    }

    @Provides
    @Singleton
    fun provideScheduler(): SchedulerProvider {
        return DefaultSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideStorageService(prefs: SharedPreferences, gson: Gson): StorageService {
        return SharedPrefsStorageService(prefs, gson)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        if (prefs == null) {
            val key = context.packageName ?: throw NullPointerException("Prefs key may not be null")
            prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        }
        return prefs!!
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}