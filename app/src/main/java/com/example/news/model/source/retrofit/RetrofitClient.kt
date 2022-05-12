package com.example.news.model.source.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        private val retrofitClient by lazy{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        val api by lazy{
            retrofitClient.create(NewsApi::class.java)
        }
    }
}