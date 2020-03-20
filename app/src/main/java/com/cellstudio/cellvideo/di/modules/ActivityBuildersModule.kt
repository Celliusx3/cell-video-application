package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.presentation.screens.details.DetailsActivity
import com.cellstudio.cellvideo.presentation.screens.livesource.LiveSourceActivity
import com.cellstudio.cellvideo.presentation.screens.main.MainActivity
import com.cellstudio.cellvideo.presentation.screens.search.SearchActivity
import com.cellstudio.cellvideo.presentation.screens.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLiveSourceActivity(): LiveSourceActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
}