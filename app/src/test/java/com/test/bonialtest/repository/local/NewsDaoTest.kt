package com.test.bonialtest.repository.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bonialtest.serviceslibrary.api.model.NewsResponse
import com.nhaarman.mockitokotlin2.mock
import com.test.bonialtest.commons.BaseTest
import com.test.bonialtest.model.NewsDataTest
import com.test.bonialtest.repository.api.extension.toNews
import com.test.bonialtest.repository.model.News
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class NewsDaoTest : BaseTest() {

    @Mock
    private val repositoryImpl: NewsDataBaseRepositoryImpl? = null

    @Mock
    private lateinit var dataBase: NewsDBHelper

    @Mock
    lateinit var observer: Observer<PagedList<News>>

    private val expectedNews = createNewsList().toNews()

    @Before
    fun initDb() {
        super.setUp()
        val context: Context = mock()
        dataBase = Room.inMemoryDatabaseBuilder(
            context,
            NewsDBHelper::class.java
        ).build()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun `should add news`() {
        val news = NewsDataTest.newsResponseTestBuilder().toNews()
        Observable.just(dataBase)
            .subscribeOn(Schedulers.io())
            .subscribe { newsDBHelper ->
                newsDBHelper.getNewsDao().saveNews(news)
                val newsLive = dataBase.getNewsDao().getNews()
                assertNotNull(newsLive)
            }
    }

    @Test
    fun `should get news`() {
        val news = NewsDataTest.newsResponseTestBuilder().toNews()
        Observable.just(dataBase)
            .subscribeOn(Schedulers.io())
            .subscribe { newsDBHelper ->
                newsDBHelper.getNewsDao().saveNews(news)
                newsDBHelper.getNewsDao().getNews(2).subscribe { result ->
                    assert(result == news.sortedWith(compareBy({ it.title }, { it.title })))
                }
            }
    }

    @Test
    fun `Check get paged news`() {
        repositoryImpl?.initDataSource(CompositeDisposable())
        stubMocks()
        repositoryImpl?.apply {
            getNewsList().observeForever(observer)
            Assert.assertEquals(expectedNews, getNewsList().value)
        }
    }

    private fun stubMocks() {
        val result = MutableLiveData<List<News>>()
        result.postValue(createNewsList().toNews())
        Mockito.doReturn(result).`when`(repositoryImpl)?.getNewsList()
    }

    private fun createNewsList(): List<NewsResponse> {
        return NewsDataTest.newsResponseTestBuilder()
    }
}
