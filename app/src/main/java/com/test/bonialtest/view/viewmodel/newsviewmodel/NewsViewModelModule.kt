package com.test.bonialtest.view.viewmodel.newsviewmodel

import com.test.bonialtest.repository.api.NewsRepository
import com.test.bonialtest.repository.api.NewsRepositoryImpl
import com.test.bonialtest.repository.local.NewsDataBaseRepository
import com.test.bonialtest.repository.local.NewsDataBaseRepositoryImpl
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBase
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NewsViewModelModule {
    @Binds
    abstract fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindNewsDataBaseRepository(newsRepository: NewsDataBaseRepositoryImpl): NewsDataBaseRepository

    @Binds
    abstract fun bindNewsDataBase(newsDataBase: NewsDataBaseImpl): NewsDataBase
}