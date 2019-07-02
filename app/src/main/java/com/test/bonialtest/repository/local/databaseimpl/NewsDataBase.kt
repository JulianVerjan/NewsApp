package com.test.bonialtest.repository.local.databaseimpl

import com.test.bonialtest.repository.model.News
import io.reactivex.Single

interface NewsDataBase {

    fun saveNews(newsList: List<News>)

    fun getNews(requestedLoadSize: Int): Single<List<News>>

    fun getNewsAfter(size: Int, from: Int): Single<List<News>>
}