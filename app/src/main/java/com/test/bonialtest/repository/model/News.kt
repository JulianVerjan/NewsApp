package com.test.bonialtest.repository.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.bonialtest.repository.local.utils.Constants.NEWS_TABLE
import com.test.bonialtest.view.utils.Constants.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Entity(tableName = NEWS_TABLE)
@Parcelize
data class News(
    @PrimaryKey
    val title: String,
    @Embedded
    val source: Source?,
    val author: String? = EMPTY_STRING,
    val description: String? = EMPTY_STRING,
    val url: String? = EMPTY_STRING,
    val urlToImage: String? = EMPTY_STRING,
    val publishedAt: String? = EMPTY_STRING,
    val content: String? = EMPTY_STRING
) : Parcelable