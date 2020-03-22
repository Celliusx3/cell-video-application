package com.cellstudio.cellvideo.data.environment

import android.content.Context

class M3UEnvironment(context: Context): BaseEnvironment(context) {
    override fun getBaseUrl(): String {
        return "https://iptv-org.github.io/"
    }
}