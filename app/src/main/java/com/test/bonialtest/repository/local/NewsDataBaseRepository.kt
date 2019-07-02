package com.test.bonialtest.repository.local

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import io.reactivex.disposables.CompositeDisposable

interface NewsDataBaseRepository {

    fun getNewsList(): LiveData<PagedList<News>>

    fun getState(): LiveData<State>

    fun initDataSource(compositeDisposable: CompositeDisposable)
}