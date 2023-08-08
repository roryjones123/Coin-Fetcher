package com.roz.coinfetcher.basicfeature.data

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.roz.coinfetcher.basicfeature.data.di.CoinModule
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.core.utils.resultOf
import kotlinx.coroutines.flow.flowOf

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
}
