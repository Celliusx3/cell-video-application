package com.cellstudio.cellvideo.data.db.dao

import androidx.room.*
import com.cellstudio.cellvideo.data.db.entity.SourceEntity
import io.reactivex.Single

@Dao
abstract class SourceDao {

    @Transaction
    @Query("SELECT * FROM Source")
    abstract fun getSources(): Single<List<SourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSource(source: SourceEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSources(sources: List<SourceEntity>): Single<List<Long>>

    @Query("DELETE FROM Source")
    abstract fun clear(): Single<Int>

    @Query("DELETE FROM Source WHERE id = :id")
    abstract fun deleteSource(id: String): Single<Int>
}