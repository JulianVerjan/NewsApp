package com.test.bonialtest.repository.api

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bonialtest.serviceslibrary.api.newsapi.NewsService
import com.test.bonialtest.repository.api.datasources.NewsDataSource
import com.test.bonialtest.repository.api.datasources.NewsDataSourceFactory
import com.test.bonialtest.repository.api.utils.PagedConstants.ITEMS_PER_PAGE
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    @NonNull private val newsService: NewsService,
    @NonNull private val newsDao: NewsDataBaseImpl
) : NewsRepository {

    @NonNull
    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var newsList: LiveData<PagedList<News>>

    private lateinit var newsDataSourceFactory: NewsDataSourceFactory

    override fun initDataSource(locale: String, compositeDisposable: CompositeDisposable) {
        this.compositeDisposable = compositeDisposable
        newsDataSourceFactory =
            NewsDataSourceFactory(
                this.compositeDisposable, newsService,
                locale, newsDao
            )
        val config = PagedList.Config.Builder()
            .setPageSize(ITEMS_PER_PAGE)
            .setInitialLoadSizeHint(ITEMS_PER_PAGE)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder<Int, News>(newsDataSourceFactory, config).build()
    }

    override fun getNewsList(): LiveData<PagedList<News>> {
        return newsList
    }

    override fun getState(): LiveData<State> = Transformations.switchMap<NewsDataSource,
            State>(newsDataSourceFactory.newsDataSourceLiveData, NewsDataSource::state)
}