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

fun interface GetHomepageDataUseCase : () -> Flow<Result<Pair<List<Coin>, List<Tag>>>>

fun getHomepageData(
    coinRepository: CoinRepository,
    tagRepository: TagRepository
): Flow<Result<Pair<List<Coin>, List<Tag>>>> = coinRepository
    .getCoins()
    .map { Result.success(it) }
    .retryWhen { cause, attempt ->
        if (cause is IOException) {
            delay(RETRY_TIME_IN_MILLIS)
            true
        } else {
            false
        }
    }
    .catch { emit(Result.failure(it)) }.combine(tagRepository.getTags()
        .map { Result.success(it) }
        .retryWhen { cause, attempt ->
            if (cause is IOException) {
                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .catch { emit(Result.failure(it)) }) { coins, tags ->
        Result.success(Pair(coins.getOrThrow(), tags.getOrThrow()))
    }
