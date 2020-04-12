package com.cellstudio.cellvideo.di.modules

import com.cellstudio.cellvideo.presentation.screens.details.DetailsFragment
import com.cellstudio.cellvideo.presentation.screens.livesource.LiveSourceFragment
import com.cellstudio.cellvideo.presentation.screens.main.PageFragment
import com.cellstudio.cellvideo.presentation.screens.search.SearchFragment
import com.cellstudio.cellvideo.presentation.screens.settings.AddSourceDialogFragment
import com.cellstudio.cellvideo.presentation.screens.settings.SettingsFragment
import com.cellstudio.cellvideo.presentation.screens.settings.SourceSettingsFragment
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
    abstract fun contributeLiveSourceFragment(): LiveSourceFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddSourceDialogFragment(): AddSourceDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeSourceSettingsFragment(): SourceSettingsFragment


//    @ContributesAndroidInjector
//    abstract fun contributeDetailsFragment(): DetailsFragment
}