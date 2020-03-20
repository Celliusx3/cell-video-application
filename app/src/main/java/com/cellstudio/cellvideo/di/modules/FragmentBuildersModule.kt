package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.presentation.screens.details.DetailsFragment
import com.cellstudio.cellvideo.presentation.screens.livesource.LiveSourceFragment
import com.cellstudio.cellvideo.presentation.screens.main.PageFragment
import com.cellstudio.cellvideo.presentation.screens.main.TextPageFragment
import com.cellstudio.cellvideo.presentation.screens.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributePageFragment(): PageFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeTextPageFragment(): TextPageFragment

    @ContributesAndroidInjector
    abstract fun contributeLiveSourceFragment(): LiveSourceFragment




//    @ContributesAndroidInjector
//    abstract fun contributeDetailsFragment(): DetailsFragment
}