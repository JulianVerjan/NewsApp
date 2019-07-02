package com.test.bonialtest.view.viewmodel.newsviewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.test.bonialtest.repository.api.NewsRepository
import com.test.bonialtest.repository.local.NewsDataBaseRepository
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dataBaseRepository: NewsDataBaseRepository
) : ViewModel() {

    @NonNull
    private val compositeDisposable = CompositeDisposable()

    private var isConnected: Boolean = false

    fun initDataSource(locale: String, isConnected: Boolean) {
        this.isConnected = isConnected
        if (isConnected) newsRepository.initDataSource(locale, compositeDisposable)
        else dataBaseRepository.initDataSource(compositeDisposable)
    }

    fun getState(): LiveData<State> = if (isConnected) newsRepository.getState()
    else dataBaseRepository.getState()

    fun getNews(): LiveData<PagedList<News>> = if (isConnected) newsRepository.getNewsList()
    else dataBaseRepository.getNewsList()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}