package com.cellstudio.cellvideo.data.entities.jike

import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.constants.Constants

data class JikePage(
    val id: Int,
    val name: String,
    val icon: Int
) {

    companion object {
        val HomePage = JikePage(1001, Constants.Home, R.drawable.ic_home_white_24dp)
    }
}