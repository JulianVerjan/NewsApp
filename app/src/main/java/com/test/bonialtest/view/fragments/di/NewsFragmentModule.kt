package com.test.bonialtest.view.fragments.di

import com.test.bonialtest.view.fragments.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindNewsFragment(): NewsFragment
}