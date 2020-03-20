package com.cellstudio.cellvideo.data.base

import android.util.Log.INFO
import com.cellstudio.cellvideo.data.api.eyunzhu.EYunZhuService
import com.cellstudio.cellvideo.data.api.jike.JikeService
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseHttpClient() : HttpClient {

    private val okHttpClient: OkHttpClient
    private lateinit var eYunZhuService: EYunZhuService
    private lateinit var jikeService: JikeService

    init {
        okHttpClient = createOkHttpClient()
        createService(okHttpClient)
    }

    override fun getEYunZhuApiService(): EYunZhuService {
        return eYunZhuService
    }

    override fun getJikeApiService(): JikeService {
        return jikeService
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
        jikeService = createJikeService(okHttpClient)
    }

    private fun createEYunZhuService(client: OkHttpClient): EYunZhuService {
        val retrofit = getRetrofit(client, "https://api.eyunzhu.com/")

        return retrofit.create(EYunZhuService::class.java)
    }

    private fun createJikeService(client: OkHttpClient): JikeService {
        val retrofit = getRetrofit(client, "http://jike.freevar.com/")

        return retrofit.create(JikeService::class.java)
    }

    private fun getRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}