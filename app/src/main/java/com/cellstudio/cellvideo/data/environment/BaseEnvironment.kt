package com.cellstudio.cellvideo.data.environment

import android.content.Context
import android.os.Build
import android.provider.Settings

abstract class BaseEnvironment(val context: Context) : Environment {

    override fun getAppName(): String {
        return ""
    }

    override fun getPackageName(): String {
        return ""
    }

    override fun getAppVersion(): String {
        return ""
    }

    override fun getBuildNumber(): String {
        return ""
    }

    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
    }

    override fun getDeviceName(): String {
        return Build.MODEL
    }

    override fun getOSName(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    override fun getOSVersion(): String {
        return Build.VERSION.RELEASE
    }
}