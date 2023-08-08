package com.roz.coinfetcher.basicfeature.domain.usecase

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.Tag
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15000L

class GetHomepageDataUseCase(
    private val coinRepository: CoinRepository,
    private val tagRepository: TagRepository
) {
    operator fun invoke(): Flow<Result<Pair<List<Coin>, List<Tag>>>> = flow {
        val coinsFlow = coinRepository.getCoins()
            .map { Result.success(it) }
            .retryWhen { cause, attempt ->
                if (cause is IOException) {
                    delay(RETRY_TIME_IN_MILLIS)
                    true
                } else {
                    false
                }
            }
            .catch { emit(Result.failure(it)) }

        val tagsFlow = tagRepository.getTags()
            .map { Result.success(it) }
            .retryWhen { cause, attempt ->
                if (cause is IOException) {
                    delay(RETRY_TIME_IN_MILLIS)
                    true
                } else {
                    false
                }
            }
            .catch { emit(Result.failure(it)) }

        coinsFlow.combine(tagsFlow) { coins, tags ->
            Result.success(Pair(coins.getOrThrow(), tags.getOrThrow()))
        }
    }
}