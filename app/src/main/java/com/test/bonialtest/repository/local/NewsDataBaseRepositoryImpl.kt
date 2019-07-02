package com.test.bonialtest.repository.local

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.bonialtest.repository.api.utils.PagedConstants.ITEMS_PER_PAGE
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import com.test.bonialtest.repository.local.datasources.NewsDataBaseSource
import com.test.bonialtest.repository.local.datasources.NewsDataBaseSourceFactory
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsDataBaseRepositoryImpl @Inject constructor(
    @NonNull private val dataBaseRepositoryImpl: NewsDataBaseImpl
) : NewsDataBaseRepository {

    private lateinit var newsList: LiveData<PagedList<News>>

    private lateinit var newsDataSourceFactory: NewsDataBaseSourceFactory

    private lateinit var compositeDisposable: CompositeDisposable

    override fun initDataSource(compositeDisposable: CompositeDisposable) {
        this.compositeDisposable = compositeDisposable
        newsDataSourceFactory =
            NewsDataBaseSourceFactory(dataBaseRepositoryImpl, this.compositeDisposable)
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

    override fun getState(): LiveData<State> = Transformations.switchMap<NewsDataBaseSource,
            State>(newsDataSourceFactory.newsDataSourceLiveData, NewsDataBaseSource::state)
}