package com.example.news.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.news.model.Article
import com.example.news.model.data.News
import com.example.news.model.repository.NewsRepository
import com.example.news.model.source.room.ArticleDatabase
import com.example.news.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(application: Application): AndroidViewModel(application){

    private val articleDao = ArticleDatabase.getDatabase(application).getArticleDao()
    private val newsRepository : NewsRepository

    val breakingNews: MutableLiveData<Resource<News>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse: News? = null

    lateinit var articles: LiveData<PagedList<Article>>

    init{
        newsRepository = NewsRepository(articleDao)
        getBreakingNews("fr")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingPageNumber)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<News>): Resource<News>? {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                breakingPageNumber++
                if(breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }
    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }
    fun getSavedArticles() = newsRepository.getAllArticles()

    fun getBreakingNews() : LiveData<PagedList<Article>>{
        return articles
    }
}