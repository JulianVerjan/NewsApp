package com.test.bonialtest.repository.local.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import com.test.bonialtest.repository.model.News
import io.reactivex.disposables.CompositeDisposable

class NewsDataBaseSourceFactory(
    dataBaseRepositoryImpl: NewsDataBaseImpl,
    compositeDisposable: CompositeDisposable
) :
    DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataBaseSource>()
    private val newsDataSource =
        NewsDataBaseSource(dataBaseRepositoryImpl, compositeDisposable)

    override fun create(): DataSource<Int, News> {
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}