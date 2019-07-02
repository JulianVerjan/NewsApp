package com.bonialtest.sericeslibrary

import com.bonialtest.serviceslibrary.api.newsapi.NewsApi
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class NewsServiceTest : BaseTest() {

    private lateinit var service: NewsApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun initService() {
        this.setUp()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        this.service = createService(NewsApi::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getNews() {
        enqueueResponse("tmdb_news.json")
        service.getNews("US", "74abf5f84a9448648ef0548cd37a723b", 0, 21)
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                assertThat(response.status, `is`("ok"))
                assertThat(response.articles[0].title, `is`("Live: Hong Kong protesters clash with police over China extradition bill - CNN"))
                assertThat(response.articles[0].publishedAt, `is`("2019-07-01T00:27:00Z"))
            }
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val classLoader = Thread.currentThread().contextClassLoader
        val inputStream = classLoader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    private fun createService(clazz: Class<NewsApi>): NewsApi {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }
}
