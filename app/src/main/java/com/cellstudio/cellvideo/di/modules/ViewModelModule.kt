package com.cellstudio.cellvideo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cellstudio.cellvideo.interactor.viewmodel.details.DetailsViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.main.MainViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.main.PageViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.search.SearchViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.settings.AddSourceViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.settings.SettingsViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.settings.SourceSettingsViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.splash.SplashViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModelImpl::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModelImpl::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModelImpl::class)
    abstract fun bindSearchViewModel(searchViewModelImpl: SearchViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModelImpl::class)
    abstract fun bindDetailsViewModel(detailsViewModelImpl: DetailsViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PageViewModelImpl::class)
    abstract fun bindPageViewModel(pageViewModelImpl: PageViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModelImpl::class)
    abstract fun bindSettingsViewModel(settingsViewModelImpl: SettingsViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SourceSettingsViewModelImpl::class)
    abstract fun bindSourceSettingsViewModel(sourceSettingsViewModelImpl: SourceSettingsViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddSourceViewModelImpl::class)
    abstract fun bindAddSourceViewModel(addSourceViewModelImpl: AddSourceViewModelImpl): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}