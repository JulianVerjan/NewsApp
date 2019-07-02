package com.test.bonialtest.commons.di

import android.app.Application
import com.bonialtest.serviceslibrary.ApiModule
import com.bonialtest.serviceslibrary.api.di.ServiceModule
import com.test.bonialtest.commons.application.App
import com.test.bonialtest.repository.local.di.DataBaseModule
import com.test.bonialtest.view.fragments.di.DetailFragmentModule
import com.test.bonialtest.view.fragments.di.NewsFragmentModule
import com.test.bonialtest.view.viewmodel.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        ActivitiesModule::class,
        ServiceModule::class,
        ViewModelModule::class,
        NewsFragmentModule::class,
        DetailFragmentModule::class,
        DataBaseModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}