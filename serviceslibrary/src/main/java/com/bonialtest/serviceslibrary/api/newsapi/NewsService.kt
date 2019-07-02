package com.bonialtest.serviceslibrary.api.newsapi

import com.bonialtest.serviceslibrary.api.model.NewsServiceResponse
import io.reactivex.Observable

interface NewsService {
    fun getNews(
        country: String,
        apiKey: String,
        page: Int,
        pageSize: Int
    ): Observable<NewsServiceResponse>
}