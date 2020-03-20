package com.cellstudio.cellvideo.data.entities.eyunzhu

import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.constants.Constants

data class EYunZhuPage(
    val id: Int,
    val name: String,
    val icon: Int
) {

    companion object {
        val HomePage = EYunZhuPage(1002, Constants.Home, R.drawable.ic_home_white_24dp)
    }
}