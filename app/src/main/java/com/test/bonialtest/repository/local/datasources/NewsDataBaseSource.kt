package com.test.bonialtest.repository.local.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.bonialtest.repository.api.utils.PagedConstants.ITEMS_PER_PAGE
import com.test.bonialtest.repository.api.utils.PagedConstants.ZERO_VALUE
import com.test.bonialtest.repository.local.databaseimpl.NewsDataBaseImpl
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class NewsDataBaseSource(
    private val dataBaseRepositoryImpl: NewsDataBaseImpl,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, News>() {

    val state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        this.state.postValue(State.LOADING)
        compositeDisposable.add(callNewsFromDB(params, callback))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        this.state.postValue(State.LOADING)
        compositeDisposable.add(callNewsFromDB(params, callback))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun callNewsFromDB(params: LoadParams<Int>, callback: LoadCallback<Int, News>): Disposable {
        return dataBaseRepositoryImpl.getNewsAfter(
            size = ITEMS_PER_PAGE,
            from = params.key
        )
            .subscribe(
                { response ->
                    this.state.postValue(State.DONE)
                    callback.onResult(response, params.key + ITEMS_PER_PAGE)
                },
                {
                    this.state.postValue(State.ERROR)
                }
            )
    }

    private fun callNewsFromDB(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>): Disposable {
        return dataBaseRepositoryImpl.getNews(requestedLoadSize = params.requestedLoadSize)
            .subscribe(
                { response ->
                    this.state.postValue(State.DONE)
                    callback.onResult(response, ZERO_VALUE, ITEMS_PER_PAGE)
                },
                {
                    this.state.postValue(State.ERROR)
                }
            )
    }
}