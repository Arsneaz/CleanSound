package com.example.cleansound.spotify

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.cleansound.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpotifyConfig (context: Context, private val accessToken: String){
    private var retrofit: Retrofit

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }
    init {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor{chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
        fun createService() : SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }

}