package com.test.bonialtest.repository.api

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.bonialtest.repository.state.State
import com.test.bonialtest.repository.model.News
import io.reactivex.disposables.CompositeDisposable

interface NewsRepository {

    fun getNewsList(): LiveData<PagedList<News>>

    fun getState(): LiveData<State>

    fun initDataSource(locale: String, compositeDisposable: CompositeDisposable)
}