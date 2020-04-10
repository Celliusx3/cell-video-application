package com.cellstudio.cellvideo.data.entities.general

class DataSource(
    val id: String,
    val label: String,
    val url: String,
    val isEditable: Boolean) {
    companion object {
        val EYUNZHU = DataSource("EYunZhu", "忆云竹", "https://api.eyunzhu.com/", false)
        val JIKE = DataSource("Jike", "极客影院", "http://jike.freevar.com/", false)
        val M3U = DataSource("M3U", "M3U", "https://iptv-org.github.io/", false)
    }
}

