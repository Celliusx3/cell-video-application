package com.cellstudio.cellvideo.domain.repository

import com.cellstudio.cellvideo.data.entities.general.DataSource
import io.reactivex.Observable

interface SettingsRepository {
    fun addNewSource(dataSource: DataSource): Observable<Long>
    fun removeSource(id: String): Observable<Int>
    fun getSources(): Observable<List<DataSource>>
    fun getSelectedSource(): DataSource?
    fun updateSelectedSource(selectedSource: DataSource)

    fun getPrivacyPolicy(): String
}