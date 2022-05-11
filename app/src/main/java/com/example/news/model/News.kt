package com.example.news.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    var articles: MutableList<Article>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int?

)
