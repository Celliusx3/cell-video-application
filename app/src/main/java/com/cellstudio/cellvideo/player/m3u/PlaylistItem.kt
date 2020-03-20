package com.cellstudio.cellvideo.player.m3u

interface PlaylistItem {
    val id: Long

    val downloaded: Boolean

    val mediaType: Int

    val mediaUrl: String?

    val downloadedMediaUri: String?

    val thumbnailUrl: String?

    val artworkUrl: String?

    val title: String?

    val album: String?

    val artist: String?
}