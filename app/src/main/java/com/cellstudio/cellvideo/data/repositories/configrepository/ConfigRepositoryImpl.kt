package com.cellstudio.cellvideo.data.repositories.configrepository

import com.cellstudio.cellvideo.constants.Constants

class ConfigRepositoryImpl: ConfigRepository {
    override fun getJikeVideoTypes(): List<String> {
        return listOf(Constants.movies, Constants.tvSeries, Constants.anime,  Constants.tvShows)
    }
}