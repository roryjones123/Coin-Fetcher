package com.roz.coinfetcher.basicfeature.data

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.roz.coinfetcher.basicfeature.data.di.CoinModule
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.data.remote.api.TagApi
import com.roz.coinfetcher.basicfeature.data.repository.DefaultCoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.core.utils.resultOf
import dagger.Binds
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.flowOf
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoinModule::class],
)
internal object FakeHomepageModule {

    @Provides
    fun provideFakeHomepageUseCase(): GetHomepageDataUseCase {
        return GetHomepageDataUseCase {
            flowOf(Result.success(
                Pair(generateTestCoinsFromDomain(), generateTestTagsFromDomain()),
            ))
        }
    }

    @Provides
    @Singleton
    fun provideFakeCoinApi(
        retrofit: Retrofit,
    ): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFakeGetCoinUseCase(
        coinRepository: CoinRepository,
    ): GetCoinUseCase {
        return GetCoinUseCase(coinRepository)
    }
}
