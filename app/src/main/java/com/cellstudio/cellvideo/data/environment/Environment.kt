package com.cellstudio.cellvideo.data.environment

interface Environment {
    fun getAppName() : String
    fun getPackageName() : String
    fun getAppVersion() : String
    fun getBuildNumber() : String
    fun getDeviceId() : String
    fun getDeviceName() : String
    fun getOSName() : String
    fun getOSVersion() : String
    fun getPrivacyUrl(): String
}