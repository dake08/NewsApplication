package com.example.news.repository

import com.example.news.model.Article
import com.example.news.repository.database.ArticleDAO
import com.example.news.repository.database.ArticleDatabase
import com.example.news.repository.service.RetrofitClient

class NewsRepository (private val articleDAO: ArticleDAO){
    suspend fun getBreakingNews(countryCode : String, pageNumber: Int) =
        RetrofitClient.api.getBreakingNews(countryCode,pageNumber)
    suspend fun getSearchingNews(q : String, pageNumber: Int) =
        RetrofitClient.api.getSearchNews(q,pageNumber)
    suspend fun insert(article: Article) = articleDAO.insert(article)
    suspend fun delete(article: Article) = articleDAO.deleteArticle(article)

    fun getAllArticles() = articleDAO.getArticles()
}