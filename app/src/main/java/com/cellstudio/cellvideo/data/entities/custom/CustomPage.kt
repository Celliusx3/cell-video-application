package com.cellstudio.cellvideo.data.entities.custom

import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.constants.Constants

data class CustomPage(
    val id: Int,
    val name: String,
    val icon: Int
) {
    companion object {
        val HomePage = CustomPage(1002, Constants.Custom, R.drawable.ic_stars_white_24dp)
    }
}