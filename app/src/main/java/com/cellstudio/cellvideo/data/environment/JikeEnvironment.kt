package com.cellstudio.cellvideo.data.environment

import android.content.Context

class JikeEnvironment(context: Context): BaseEnvironment(context) {
    override fun getBaseUrl(): String {
        return "http://jike.freevar.com/"
    }
}