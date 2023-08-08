package com.roz.coinfetcher.basicfeature.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.data.remote.api.TagApi
import com.roz.coinfetcher.basicfeature.data.repository.DefaultCoinRepository
import com.roz.coinfetcher.basicfeature.data.repository.DefaultTagRepository
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.getHomepageDataUseCase
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
    @Singleton
    fun provideTagApi(
        retrofit: Retrofit,
    ): TagApi {
        return retrofit.create(TagApi::class.java)
    }

    @Provides
    fun provideGetCoinsUseCase(
        coinRepository: CoinRepository,
        tagRepository: TagRepository
    ): GetHomepageDataUseCase {
        return GetHomepageDataUseCase {
            getHomepageDataUseCase(coinRepository = coinRepository, tagRepository = tagRepository)
        }
    }

    @Provides
    @Singleton
    fun provideGetCoinUseCase(
        coinRepository: CoinRepository,
    ): GetCoinUseCase {
        return GetCoinUseCase(coinRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindCoinRepository(defaultCoinRepo: DefaultCoinRepository): CoinRepository

        @Binds
        @Singleton
        fun bindTagRepository(defaultTagRepo: DefaultTagRepository): TagRepository
    }
}
