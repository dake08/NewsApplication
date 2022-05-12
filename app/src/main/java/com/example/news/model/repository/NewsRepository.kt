package com.example.news.model.repository

import com.example.news.model.Article
import com.example.news.model.source.room.ArticleDAO
import com.example.news.model.source.retrofit.RetrofitClient

class NewsRepository (private val articleDAO: ArticleDAO){
    suspend fun getBreakingNews(countryCode : String, pageNumber: Int) =
        RetrofitClient.api.getBreakingNews(countryCode,pageNumber)
    suspend fun insert(article: Article) = articleDAO.insert(article)
    suspend fun delete(article: Article) = articleDAO.deleteArticle(article)

    fun getAllArticles() = articleDAO.getArticles()
}