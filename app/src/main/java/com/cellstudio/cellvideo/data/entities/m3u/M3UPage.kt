package com.cellstudio.cellvideo.data.entities.m3u

import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.constants.Constants

data class M3UPage(
    val id: Int,
    val name: String,
    val icon: Int
) {

    companion object {
        val HomePage = M3UPage(1002, Constants.Home, R.drawable.ic_home_white_24dp)
    }
}