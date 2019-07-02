package com.bonialtest.serviceslibrary.api.model

import com.google.gson.annotations.SerializedName

class NewsServiceResponse(
    @SerializedName("status") val status: String,
    @SerializedName("articles") val articles: List<NewsResponse>
)