package com.roz.coinfetcher.basicfeature.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import com.roz.coinfetcher.basicfeature.presentation.HomepageNavigationFactory
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState
import com.roz.coinfetcher.core.navigation.NavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object CoinsViewModelModule {

    @Provides
    fun provideInitialCoinsUiState(): HomepageUiState = HomepageUiState()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface CoinsSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindCoinsNavigationFactory(factory: HomepageNavigationFactory): NavigationFactory
}
