package com.cellstudio.cellvideo.data.environment

import android.content.Context
import com.cellstudio.cellvideo.constants.Constants

class EYunZhuEnvironment(context: Context): BaseEnvironment(context) {
    override fun getBaseUrl(): String {
        return "https://api.eyunzhu.com/"
    }
}