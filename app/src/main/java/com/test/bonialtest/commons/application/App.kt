package com.test.bonialtest.commons.application

import android.app.Activity
import android.app.Application
import android.app.Service
import com.test.bonialtest.commons.di.AppModule
import com.test.bonialtest.commons.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasServiceInjector {

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var serviceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityDispatchingAndroidInjector
    override fun serviceInjector(): DispatchingAndroidInjector<Service> = serviceDispatchingAndroidInjector
}