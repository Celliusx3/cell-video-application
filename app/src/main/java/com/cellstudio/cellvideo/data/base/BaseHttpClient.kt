package com.cellstudio.cellvideo.data.base

import android.util.Log.INFO
import com.cellstudio.cellvideo.data.api.eyunzhu.EYunZhuService
import com.cellstudio.cellvideo.data.api.m3u.M3UService
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseHttpClient : HttpClient {

    private val okHttpClient: OkHttpClient
    private lateinit var eYunZhuService: EYunZhuService
    private lateinit var m3uService: M3UService

    init {
        okHttpClient = createOkHttpClient()
        createService(okHttpClient)
    }

    override fun getEYunZhuApiService(): EYunZhuService {
        return eYunZhuService
    }

    override fun getM3UApiService(): M3UService {
        return m3uService
    }

    private fun createOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message -> Platform.get().log(INFO, message, null) }
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private fun createService(okHttpClient: OkHttpClient) {
        eYunZhuService = createEYunZhuService(okHttpClient)
        m3uService = createM3UService(okHttpClient)
    }

    private fun createEYunZhuService(client: OkHttpClient): EYunZhuService {
        val retrofit = getRetrofit(client, "https://api.eyunzhu.com/", GsonConverterFactory.create())

        return retrofit.create(EYunZhuService::class.java)
    }

    private fun createM3UService(client: OkHttpClient): M3UService {
        val retrofit = getRetrofit(client, "https://iptv-org.github.io/", M3UConverterFactory())

        return retrofit.create(M3UService::class.java)
    }

//    iptv/countries/my.m3u
    private fun getRetrofit(client: OkHttpClient, baseUrl: String, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}