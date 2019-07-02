package com.test.bonialtest.view.activities.di

import com.test.bonialtest.view.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun bindNewsActivity(): MainActivity
}