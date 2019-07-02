package com.test.bonialtest.model

import com.bonialtest.serviceslibrary.api.model.NewsResponse
import com.bonialtest.serviceslibrary.api.model.SourceResponse

object NewsDataTest {

    fun newsResponseTestBuilder(): List<NewsResponse> {
        val list = ArrayList<NewsResponse>()
        list.add(
            NewsResponse(
                SourceResponse("1", "Fake"), "Julian Verjan",
                "Test of the news 1", "This the fake news for the test!!", "url", "01-11-2019",
                "This is the test of news one"
            )
        )

        list.add(
            NewsResponse(
                SourceResponse("2", "Fake 2"), "Julian Verjan",
                "Test of the news 2", "This the fake news for the test 2!!", "url", "01-11-2019",
                "This is the test of news two"
            )
        )

        list.add(
            NewsResponse(
                SourceResponse("3", "Fake 3"), "Julian Verjan",
                "Test of the news 3", "This the fake news for the test 3!!", "url", "01-11-2019",
                "This is the test of news three"
            )
        )

        return list
    }

    fun sourceTestBuilder(): SourceResponse {
        return SourceResponse("CNN", "CNN NEWS")
    }
}