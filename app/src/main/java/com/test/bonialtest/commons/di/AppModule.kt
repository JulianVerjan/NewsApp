package com.test.bonialtest.commons.di

import android.content.Context
import com.test.bonialtest.commons.application.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(private var app: App) {

    @Provides
    fun provideContext(): Context {
        return app
    }
}