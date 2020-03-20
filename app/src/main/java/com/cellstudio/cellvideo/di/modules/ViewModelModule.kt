package com.cellstudio.cellvideo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cellstudio.cellvideo.interactor.viewmodel.details.DetailsViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.main.LiveSourceMainViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.main.MainViewModelImpl
import com.cellstudio.cellvideo.interactor.viewmodel.search.SearchViewModelImpl
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
    @ViewModelKey(LiveSourceMainViewModelImpl::class)
    abstract fun bindLiveSourceMainViewModel(liveSourceMainViewModelImpl: LiveSourceMainViewModelImpl): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}