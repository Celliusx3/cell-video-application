package com.cellstudio.cellvideo.data.parser.m3u

import android.util.Log
import com.cellstudio.cellvideo.data.entities.m3u.general.M3UItem
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

object M3UParser {
    var CHANNEL_REGEX = "EXTINF:(.+),(.+)(?:\\R)(.+)$"
    var METADATA_REGEX = "(\\S+?)=\"(.+?)\""
    private var metadata_pattern: Pattern = Pattern.compile(METADATA_REGEX)
    private var channel_pattern: Pattern = Pattern.compile(CHANNEL_REGEX, Pattern.MULTILINE)
    fun parse(playlist: InputStream): MutableList<M3UItem> {
        val cl = mutableListOf<M3UItem>()
        val s = Scanner(playlist).useDelimiter("#")
        Log.d("HOLINO", s.next().trim { it <= ' ' })
        require(!s.next().trim { it <= ' ' }.contains("EXTM3U"))
        while (s.hasNext()) {
            val line = s.next()
            val matcher = channel_pattern.matcher(line)
            if (matcher.find()) {
                val item = M3UItem(
                    matcher.group(2),
                    matcher.group(3),
                    parseMetadata(matcher.group(1) ?: "", metadata_pattern)
                )
                cl.add(item)
            }
        }
        return cl
    }

    private fun parseMetadata(metadata: String, metadataPattern: Pattern): Map<String, String> {
        val matcher = metadataPattern.matcher(metadata)
        val hashMap = hashMapOf<String, String>()
        while (matcher.find()) {
            val group1 = matcher.group(1)
            val group2 = matcher.group(2)
            if (group1 != null && group2 != null) {
                hashMap[group1] = group2
            }
        }
        return hashMap
    }
}


