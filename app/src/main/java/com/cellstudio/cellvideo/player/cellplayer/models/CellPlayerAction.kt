package com.cellstudio.cellvideo.player.cellplayer.models

import android.view.View

class CellPlayerAction (
    var text: String,
    var enabled: Boolean,
    var icon: Int,
    val action: View.OnClickListener
)
//
//interface CellPlayerActionListener {
//    fun onClick(text: String)
//}