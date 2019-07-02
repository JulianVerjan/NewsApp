package com.bonialtest.serviceslibrary.api.newsapi

import com.bonialtest.serviceslibrary.api.model.NewsServiceResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<NewsServiceResponse>

}
