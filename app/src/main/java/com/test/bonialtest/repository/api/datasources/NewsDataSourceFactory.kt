package com.test.bonialtest.repository.api.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bonialtest.serviceslibrary.api.newsapi.NewsService
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import com.test.bonialtest.repository.model.News
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
    compositeDisposable: CompositeDisposable,
    newsService: NewsService,
    locale: String,
    newsDao: NewsDataBaseImpl
) : DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()
    private val newsDataSource =
        NewsDataSource(
            newsService, compositeDisposable,
            locale, newsDao
        )

    override fun create(): DataSource<Int, News> {
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}