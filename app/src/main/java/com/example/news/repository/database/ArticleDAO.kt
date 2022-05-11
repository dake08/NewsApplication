package com.example.news.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news.model.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insert(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}