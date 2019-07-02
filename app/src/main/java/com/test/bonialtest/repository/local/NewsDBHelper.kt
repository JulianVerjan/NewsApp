package com.test.bonialtest.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.bonialtest.repository.local.dao.NewsDao
import com.test.bonialtest.repository.local.dao.SourceDao
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.model.Source

@Database(entities = [Source::class, News::class], version = 1)
abstract class NewsDBHelper : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
    abstract fun getSourceDao(): SourceDao
}