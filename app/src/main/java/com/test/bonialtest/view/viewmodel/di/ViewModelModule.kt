package com.test.bonialtest.view.viewmodel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.bonialtest.commons.di.scope.ViewModelKey
import com.test.bonialtest.view.viewmodel.modelfactory.GenericViewModelFactory
import com.test.bonialtest.view.viewmodel.newsviewmodel.NewsViewModel
import com.test.bonialtest.view.viewmodel.newsviewmodel.NewsViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [NewsViewModelModule::class])
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(newsViewModel: NewsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GenericViewModelFactory): ViewModelProvider.Factory
}