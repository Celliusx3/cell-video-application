package com.cellstudio.cellvideo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cellstudio.cellvideo.BuildConfig
import com.cellstudio.cellvideo.data.db.converter.AppConverter
import com.cellstudio.cellvideo.data.db.dao.SourceDao
import com.cellstudio.cellvideo.data.db.entity.SourceEntity
import com.cellstudio.cellvideo.data.db.mapper.RoomEntityMapper
import com.cellstudio.cellvideo.data.entities.general.DataSource
import java.util.concurrent.Executors


@Database(entities = [ SourceEntity::class ], version = 1)
@TypeConverters(AppConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun SourceDao(): SourceDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val callback: RoomDatabase.Callback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                    INSTANCE?.SourceDao()?.insertSources(listOf(DataSource.EYUNZHU, DataSource.JIKE, DataSource.M3U).map {RoomEntityMapper.create(it)})
                })
            }
        }

        fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {
                val appDatabaseBuilder = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "cellvideo-db")
                    .addCallback(callback)

                if (BuildConfig.DEBUG)
                    appDatabaseBuilder.fallbackToDestructiveMigration()

                INSTANCE = appDatabaseBuilder
                    .build()
            }

            return INSTANCE!!
        }
    }

}