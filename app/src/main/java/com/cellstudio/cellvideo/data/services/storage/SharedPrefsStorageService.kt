package com.cellstudio.cellvideo.data.services.storage

import android.content.SharedPreferences
import com.cellstudio.cellvideo.data.entities.general.DataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsStorageService(private val prefs: SharedPreferences,
                                private val gson: Gson) : StorageService {

    override fun getBoolean(key: String): Boolean = prefs.getBoolean(key, false)

    override fun setBoolean(key: String, value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun setLong(key: String, value: Long) {
        val editor = prefs.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    override fun setSelectedDataSource(key: String, value: DataSource) {
        val json = gson.toJson(value)
        commitString(key, json)
    }

    private fun commitString(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getSelectedDataSource(key: String): DataSource? {
        val json = prefs.getString(key, "")
        val type = object : TypeToken<DataSource>() {}.type

        return gson.fromJson(json, type) ?: null
    }

    override fun getLong(key: String): Long {
        return prefs.getLong(key, 0)
    }

    override fun getAllKeys(): Set<String> {
        return prefs.all.keys
    }

    override fun removeString(key: String) {
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }

    override fun getString(key: String): String {
        return prefs.getString(key, "")!!
    }

    override fun setString(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

}
