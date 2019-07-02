package com.test.bonialtest.repository.api.extension

import com.bonialtest.serviceslibrary.api.model.NewsResponse
import com.bonialtest.serviceslibrary.api.model.SourceResponse
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.model.Source

fun List<NewsResponse>.toNews(): List<News> {
    val result = ArrayList<News>()
    this.forEach {
        result.add(
            News(
                it.title,
                it.source.toSource(), it.author,
                it.description, it.url,
                it.urlToImage, it.publishedAt, it.content
            )
        )
    }
    return result
}

fun SourceResponse.toSource(): Source {
    return Source(this.id, this.name)
}