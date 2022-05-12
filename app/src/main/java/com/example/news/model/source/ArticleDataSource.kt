package com.example.news.model.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.news.model.Article
import com.example.news.model.data.News
import com.example.news.model.source.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ArticleDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>(){

    val breakingNews: MutableLiveData<MutableList<Article>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse: News? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch{
            try{
                val response = RetrofitClient.api.getBreakingNews("fr",1,"0f77dc2c8a3548dfabce9b5e8580cd89")
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let{
                            breakingNews.postValue(it)
                            callback.onResult(it,null,2)
                        }
                    }
                }

            }catch (exception: Exception){
                Log.e("DataSource:: ", exception.message.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try{
            scope.launch{
                val response = RetrofitClient.api.getBreakingNews("fr",params.requestedLoadSize,"0f77dc2c8a3548dfabce9b5e8580cd89")
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let{

                            callback.onResult(it,params.key + 1)
                        }
                    }
                }
            }
        }catch (exception: Exception){
            Log.e("DataSource:: ", exception.message.toString())
        }
    }
}

