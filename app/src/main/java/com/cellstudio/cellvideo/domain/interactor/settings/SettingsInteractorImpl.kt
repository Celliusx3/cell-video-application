package com.cellstudio.cellvideo.domain.interactor.settings

import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.cellstudio.cellvideo.domain.repository.DataSourceRepository
import com.cellstudio.cellvideo.domain.repository.SettingsRepository
import com.cellstudio.cellvideo.interactor.model.domainmodel.DataSourceModel
import io.reactivex.Observable

class SettingsInteractorImpl(private val repository: SettingsRepository, private val dataSourceRepository: DataSourceRepository): SettingsInteractor {
    override fun addNewSource(dataSourceModel: DataSourceModel):Observable<Boolean> {
        return repository.addNewSource(DataSource(dataSourceModel.id, dataSourceModel.label,dataSourceModel.url, dataSourceModel.isEditable))
            .map {true}
    }

    override fun removeNewSource(id: String): Observable<Boolean> {
        return repository.removeSource(id)
            .map {true}
    }

    override fun getDataSources(): Observable<List<DataSourceModel>> {
        return repository.getSources().map {
            it.map {
                DataSourceModel.create(it)
            }
        }
    }

    override fun getSelectedDataSource(): DataSourceModel? {
        return repository.getSelectedSource()?.let {
            DataSourceModel.create(it)
        }
    }

    override fun updateSelectedDataSource(dataSourceModel: DataSourceModel) {
        repository.updateSelectedSource(DataSource(dataSourceModel.id, dataSourceModel.label,dataSourceModel.url, dataSourceModel.isEditable))
    }

    override fun validateDateSource(url: String): Observable<Boolean> {
        return dataSourceRepository.validateDataSource(url)
    }
}