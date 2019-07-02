package com.test.bonialtest.model

import com.test.bonialtest.commons.BaseTest
import com.test.bonialtest.repository.api.extension.toNews
import com.test.bonialtest.repository.api.extension.toSource
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ExtensionTest : BaseTest() {

    @Test
    fun newsIsMappedCorrectly() {
        val newsTest = NewsDataTest.newsResponseTestBuilder()
        val news = newsTest.toNews()
        var count = 1
        news.forEach {
            assertThat(it.title, `is`(equalTo("Test of the news $count")))
            count++
        }
    }

    @Test
    fun sourceIsMappedCorrectly() {
        val sourceTest = NewsDataTest.sourceTestBuilder()
        val source = sourceTest.toSource()
        assertThat(source.id, `is`(equalTo("CNN")))
    }
}