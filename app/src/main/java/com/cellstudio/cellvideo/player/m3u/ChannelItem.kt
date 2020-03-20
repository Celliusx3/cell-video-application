package com.cellstudio.cellvideo.player.m3u

import java.io.Serializable

class ChannelItem : PlaylistItem, Serializable {
    var duration = 0
    var name: String ?= null
    var url: String ?= null
    override var title: String? = null
    override var mediaUrl: String? = null
    var metadata: Map<String, String>? = null
    //    public ChannelItem() {
//        metadata = new HashMap<String, String>();
//    }
    override val album: String?
        get() = "album"

    override val artist: String?
        get() = "artist"

    override val artworkUrl: String?
        get() = "url"

    override val downloaded: Boolean
        get() = false

    override val downloadedMediaUri: String?
        get() = ""

    override val id: Long
        get() = 0

    override val mediaType: Int
        get() = 1

    override val thumbnailUrl: String?
        get() = ""

}

