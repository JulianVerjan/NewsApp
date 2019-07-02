package com.test.bonialtest.view.fragments.di

import com.test.bonialtest.view.fragments.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindDetailFragment(): DetailFragment
}