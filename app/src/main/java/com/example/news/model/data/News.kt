package com.example.news.model.data

import com.example.news.model.Article
import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    var articles: MutableList<Article>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int?

)
