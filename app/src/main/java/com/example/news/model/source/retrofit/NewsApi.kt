package com.example.news.model.source.retrofit

import com.example.news.model.data.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String = "fr",
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = "0f77dc2c8a3548dfabce9b5e8580cd89"
    ) : Response<News>
}