package com.test.bonialtest.repository.local.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.test.bonialtest.repository.local.NewsDBHelper
import com.test.bonialtest.repository.local.dao.NewsDao
import com.test.bonialtest.repository.local.dao.SourceDao
import com.test.bonialtest.repository.local.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): NewsDBHelper {
        return Room.databaseBuilder(application, NewsDBHelper::class.java, DB_NAME).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(@NonNull database: NewsDBHelper): NewsDao {
        return database.getNewsDao()
    }

    @Provides
    @Singleton
    fun provideSourceDao(@NonNull database: NewsDBHelper): SourceDao {
        return database.getSourceDao()
    }
}
