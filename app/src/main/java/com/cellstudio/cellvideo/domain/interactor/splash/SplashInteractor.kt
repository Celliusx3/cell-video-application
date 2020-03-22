package com.cellstudio.cellvideo.domain.interactor.splash

import com.cellstudio.cellvideo.interactor.model.domainmodel.PageModel

interface SplashInteractor {
    fun getPageModels(): List<PageModel>

}