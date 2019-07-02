package com.test.bonialtest.view.activities.di

import com.test.bonialtest.view.activities.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailActivityModule {

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity
}