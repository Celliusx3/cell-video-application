package com.cellstudio.cellvideo.player.cellplayer.utils

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.util.Util.toLowerInvariant



object Utils{
    @C.ContentType
    fun inferContentType(fileName: String): Int {
        val tempFileName = toLowerInvariant(fileName)
        return if (tempFileName.endsWith(".mpd")) {
            C.TYPE_DASH
        } else if (tempFileName.endsWith(".m3u8")) {
            C.TYPE_HLS
        } else if (tempFileName.matches(".*\\.ism(l)?(/manifest(\\(.+\\))?)?".toRegex())) {
            C.TYPE_SS
        } else {
            C.TYPE_OTHER
        }
    }
}