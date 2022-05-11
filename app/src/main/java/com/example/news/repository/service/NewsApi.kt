package com.example.news.repository.service

import com.example.news.model.News
import com.example.news.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String = "us",
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<News>
    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<News>
}