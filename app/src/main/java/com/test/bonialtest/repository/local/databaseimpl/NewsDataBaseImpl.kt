package com.test.bonialtest.repository.local.databaseimpl

import androidx.annotation.NonNull
import com.test.bonialtest.repository.local.dao.NewsDao
import com.test.bonialtest.repository.model.News
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsDataBaseImpl @Inject constructor(
    @NonNull private val newsDao: NewsDao
) : NewsDataBase {

    override fun saveNews(newsList: List<News>) {
        newsDao.saveNews(newsList)
    }

    override fun getNews(requestedLoadSize: Int): Single<List<News>> =
        newsDao.getNews(requestedLoadSize)

    override fun getNewsAfter(size: Int, from: Int): Single<List<News>> =
        newsDao.getNewsAfter(size, from)
}