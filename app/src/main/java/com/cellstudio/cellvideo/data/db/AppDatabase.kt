package com.cellstudio.cellvideo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cellstudio.cellvideo.BuildConfig
import com.cellstudio.cellvideo.data.db.dao.SourceDao
import com.cellstudio.cellvideo.data.db.entity.SourceEntity
import java.util.concurrent.Executors


@Database(entities = [ SourceEntity::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun SourceDao(): SourceDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val callback: RoomDatabase.Callback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                    INSTANCE?.SourceDao()?.clear()
//                    INSTANCE?.SourceDao()?.insertSources(listOf(DataSource.EYUNZHU, DataSource.M3U).map {RoomEntityMapper.create(it)})
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