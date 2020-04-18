package com.cellstudio.cellvideo.domain.interactor.settings

import com.cellstudio.cellvideo.interactor.model.domainmodel.DataSourceModel
import io.reactivex.Observable

interface SettingsInteractor {
    fun addNewSource(dataSource: DataSourceModel): Observable<Boolean>
    fun removeNewSource(id: String): Observable<Boolean>
    fun getDataSources(): Observable<List<DataSourceModel>>
    fun getSelectedDataSource(): DataSourceModel?
    fun updateSelectedDataSource(dataSourceModel: DataSourceModel)
    fun validateDateSource(url: String): Observable<Boolean>

    fun getPrivacyPolicy(): String
}