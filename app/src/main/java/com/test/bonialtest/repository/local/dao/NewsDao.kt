package com.test.bonialtest.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.bonialtest.repository.model.News
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM NEWS")
    fun getNews(): Single<List<News>>

    @Query("SELECT * FROM NEWS LIMIT :requestedLoadSize")
    fun getNews(requestedLoadSize: Int): Single<List<News>>

    @Query("SELECT * FROM NEWS LIMIT :size OFFSET :from")
    fun getNewsAfter(size: Int, from: Int): Single<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(newsList: List<News>)
}