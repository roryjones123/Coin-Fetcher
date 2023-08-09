package com.roz.coinfetcher.basicfeature.domain.usecase

import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GetCoinUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(
        id: String,
    ): Flow<Result<ComplexCoin>> =
        coinRepository.getCoin(id).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }
}