package com.test.bonialtest.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bonialtest.serviceslibrary.api.model.NewsResponse
import com.nhaarman.mockitokotlin2.mock
import com.test.bonialtest.commons.BaseTest
import com.test.bonialtest.model.NewsDataTest
import com.test.bonialtest.repository.api.NewsRepositoryImpl
import com.test.bonialtest.repository.api.extension.toNews
import com.test.bonialtest.repository.model.News
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn

class NewsRepositoryImplTest : BaseTest() {

    private var repositoryImpl = mock<NewsRepositoryImpl>()

    private val expectedNews = createNewsList().toNews()

    @Mock
    lateinit var observer: Observer<PagedList<News>>

    @Before
    override fun setUp() {
        super.setUp()
    }

    private fun createNewsList(): List<NewsResponse> {
        return NewsDataTest.newsResponseTestBuilder()
    }

    @Test
    fun `Check get news`() {
        repositoryImpl.initDataSource("US", CompositeDisposable())
        stubMocks()
        repositoryImpl.getNewsList().observeForever(observer)
        Assert.assertEquals(expectedNews, repositoryImpl.getNewsList().value)
    }

    private fun stubMocks() {
        val result = MutableLiveData<List<News>>()
        result.postValue(createNewsList().toNews())
        doReturn(result).`when`(repositoryImpl).getNewsList()
    }
}