package com.cellstudio.cellvideo.data.entities.m3u

data class M3UFilterList(
    val type: String,
    val filters: List<M3UFilter>
)

data class M3UFilter(
    val id: String,
    val displayText: String
) {
    companion object {
        fun create(model: M3UCountry): M3UFilter {
            return M3UFilter(
                model.id,
                model.displayText
            )
        }

        fun create(model: M3UCategory): M3UFilter {
            return M3UFilter(
                model.id,
                model.id.capitalize()
            )
        }

        fun create(model: M3ULanguage): M3UFilter {
            return M3UFilter(
                model.id,
                model.displayText
            )
        }
    }
}