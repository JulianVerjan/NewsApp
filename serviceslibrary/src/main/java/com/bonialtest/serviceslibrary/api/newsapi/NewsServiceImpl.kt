package com.bonialtest.serviceslibrary.api.newsapi

import com.bonialtest.serviceslibrary.api.model.NewsServiceResponse
import io.reactivex.Observable
import javax.inject.Inject

class NewsServiceImpl
@Inject constructor(private val newsApi: NewsApi) : NewsService {
    override fun getNews(
        country: String,
        apiKey: String,
        page: Int,
        pageSize: Int
    ): Observable<NewsServiceResponse> {
        return newsApi.getNews(country, apiKey, page, pageSize)
    }


}