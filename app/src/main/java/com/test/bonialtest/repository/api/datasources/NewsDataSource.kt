package com.test.bonialtest.repository.api.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bonialtest.serviceslibrary.api.newsapi.NewsService
import com.test.bonialtest.repository.api.extension.toNews
import com.test.bonialtest.repository.api.utils.PagedConstants.APIKEY
import com.test.bonialtest.repository.api.utils.PagedConstants.ITEMS_PER_PAGE
import com.test.bonialtest.repository.api.utils.PagedConstants.PLUS_ONE_VALUE
import com.test.bonialtest.repository.api.utils.PagedConstants.ZERO_VALUE
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class NewsDataSource(
    private val newsService: NewsService,
    private val compositeDisposable: CompositeDisposable,
    private val locale: String,
    private val newsDao: NewsDataBaseImpl
) : PageKeyedDataSource<Int, News>() {

    val state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        updateState(State.LOADING)
        compositeDisposable.add(callNewsFromService(callback))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        updateState(State.LOADING)
        compositeDisposable.add(callNewsFromService(params, callback))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun callNewsFromService(params: LoadParams<Int>, callback: LoadCallback<Int, News>): Disposable {
        return newsService.getNews(locale, APIKEY, params.key, ITEMS_PER_PAGE)
            .subscribe(
                { response ->
                    updateState(State.DONE)
                    response.articles.toNews().let {
                        newsDao.saveNews(it)
                        callback.onResult(it, params.key + PLUS_ONE_VALUE)
                    }
                },
                {
                    updateState(State.ERROR)
                }
            )
    }

    private fun callNewsFromService(callback: LoadInitialCallback<Int, News>): Disposable {
        return newsService.getNews(locale, APIKEY, ZERO_VALUE, ITEMS_PER_PAGE)
            .subscribe(
                { response ->
                    updateState(State.DONE)
                    response.articles.toNews().let {
                        newsDao.saveNews(it)
                        callback.onResult(it, ZERO_VALUE, PLUS_ONE_VALUE)
                    }
                },
                {
                    updateState(State.ERROR)
                }
            )
    }
}