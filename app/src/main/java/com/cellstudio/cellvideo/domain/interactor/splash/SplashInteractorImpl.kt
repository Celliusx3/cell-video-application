package com.cellstudio.cellvideo.domain.interactor.splash

import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel

class SplashInteractorImpl(private val repository: DataSourceRepository): SplashInteractor {
    override fun getPageModels(): List<PageModel> {
        return repository.getPages()
    }
}