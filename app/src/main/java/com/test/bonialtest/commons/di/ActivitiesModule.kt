package com.test.bonialtest.commons.di

import com.test.bonialtest.view.activities.MainActivity
import com.test.bonialtest.commons.di.scope.ActivityScoped
import com.test.bonialtest.view.activities.DetailActivity
import com.test.bonialtest.view.activities.di.DetailActivityModule
import com.test.bonialtest.view.activities.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun contributeActivityAndroidInjector(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(DetailActivityModule::class)])
    abstract fun contributeDetailActivityAndroidInjector(): DetailActivity
}