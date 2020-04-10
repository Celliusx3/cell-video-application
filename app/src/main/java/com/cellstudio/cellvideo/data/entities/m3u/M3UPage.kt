package com.cellstudio.cellvideo.data.entities.m3u

import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.constants.Constants

data class M3UPage(
    val id: Int,
    val name: String,
    val icon: Int,
    val filter: M3UFilterList,
    val datas: Map<String, String>
) {
    companion object {
        val CountryPage = M3UPage(1001, Constants.Country, R.drawable.ic_country_white_24dp,
            M3UFilterList(Constants.Country, M3UCountry.values().toList().map {M3UFilter.create(it)}), hashMapOf(Pair(Constants.type, Constants.Country), Pair(Constants.id, M3UCountry.CHINA.id)))
        val LanguagePage = M3UPage(1002, Constants.Language, R.drawable.ic_stars_white_24dp, M3UFilterList(Constants.Language, M3ULanguage.values().toList().map {M3UFilter.create(it)}),hashMapOf(Pair(Constants.type, Constants.Language), Pair(Constants.id, M3ULanguage.CHINESE.id)))
        val CategoryPage = M3UPage(1003, Constants.Category, R.drawable.ic_category_white_24dp, M3UFilterList(Constants.Category, M3UCategory.values().toList().map {M3UFilter.create(it)}), hashMapOf(Pair(Constants.type, Constants.Category), Pair(Constants.id, M3UCategory.DOCUMENTARY.id)))
    }
}