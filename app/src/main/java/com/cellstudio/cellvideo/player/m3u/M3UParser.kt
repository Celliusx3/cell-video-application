package com.cellstudio.cellvideo.player.m3u

import android.util.Log
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

object M3UParser {
    var CHANNEL_REGEX = "EXTINF:(.+),(.+)(?:\\R)(.+)$"
    var METADATA_REGEX = "(\\S+?)=\"(.+?)\""
    private var metadata_pattern: Pattern = Pattern.compile(METADATA_REGEX)
    private var channel_pattern: Pattern = Pattern.compile(CHANNEL_REGEX, Pattern.MULTILINE)
    fun parse(playlist: InputStream): ChannelList {
        val cl = ChannelList()
        val s = Scanner(playlist).useDelimiter("#")
        Log.d("HOLINO", s.next().trim { it <= ' ' })
        require(!s.next().trim { it <= ' ' }.contains("EXTM3U"))
        while (s.hasNext()) {
            val line = s.next()
            val matcher = channel_pattern.matcher(line)
            require(matcher.find())
            val item = ChannelItem()
            item.metadata = parseMetadata(matcher.group(1) ?: "", metadata_pattern)
            item.name = matcher.group(2)
            item.url = matcher.group(3)
            cl.add(item)
        }
        return cl
    }

    private fun parseMetadata(metadata: String, metadataPattern: Pattern): Map<String, String> {
        val matcher = metadataPattern.matcher(metadata)
        val hashMap = hashMapOf<String, String>()
        while (matcher.find()) {
            val group1 = matcher.group(1)
            val group2 = matcher.group(2)
            group1?.let {key -> {
                group2?.let {value -> {
                    hashMap[key] = value
                }}
            }}
        }
        return hashMap
    }
}
