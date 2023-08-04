package com.roz.coinfetcher.basicfeature.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.data.repository.CoinRepositoryImpl
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinsUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.RefreshCoinsUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.getCoins
import com.roz.coinfetcher.basicfeature.domain.usecase.refreshCoins
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoinModule {

    @Provides
    @Singleton
    fun provideCoinApi(
        retrofit: Retrofit,
    ): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    @Provides
    fun provideGetCoinsUseCase(
        coinRepository: CoinRepository,
    ): GetCoinsUseCase {
        return GetCoinsUseCase {
            getCoins(coinRepository)
        }
    }

    @Provides
    fun provideRefreshCoinsUseCase(
        coinRepository: CoinRepository,
    ): RefreshCoinsUseCase {
        return RefreshCoinsUseCase {
            refreshCoins(coinRepository)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
    }
}
