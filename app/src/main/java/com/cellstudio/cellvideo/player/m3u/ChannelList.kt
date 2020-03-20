package com.cellstudio.cellvideo.player.m3u

import java.io.Serializable
import java.util.*

class ChannelList : Serializable {
    var name: String? = null
    var items: MutableList<ChannelItem> = ArrayList()
    var groups: List<String>? = null
    fun add(item: ChannelItem) {
        items.add(item)
    }

}
