package com.roz.coinfetcher.basicfeature.domain.usecase

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface GetCoinsUseCase : () -> Flow<Result<List<Coin>>>

fun getCoins(
    coinRepository: CoinRepository,
): Flow<Result<List<Coin>>> = coinRepository
    .getCoins()
    .map {
        Result.success(it)
    }
    .retryWhen { cause, _ ->
        Timber.tag("Times Test").e(cause, "exception")

        if (cause is IOException) {
            emit(Result.failure(cause))
            delay(RETRY_TIME_IN_MILLIS)
            true
        } else {
            false
        }
    }
    .catch {
        emit(Result.failure(it))
    }
