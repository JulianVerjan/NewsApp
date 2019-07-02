package com.bonialtest.serviceslibrary.api.di

import com.bonialtest.serviceslibrary.api.newsapi.NewsApi
import com.bonialtest.serviceslibrary.api.newsapi.NewsService
import com.bonialtest.serviceslibrary.api.newsapi.NewsServiceImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ServiceModule {

    @Provides
    fun provideUserService(newsServiceImpl: NewsServiceImpl): NewsService {
        return newsServiceImpl
    }

    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}