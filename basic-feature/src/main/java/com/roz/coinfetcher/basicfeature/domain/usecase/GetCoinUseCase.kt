package com.roz.coinfetcher.basicfeature.domain.usecase

import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import timber.log.Timber

class GetCoinUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(
        id: String,
    ): Result<ComplexCoin> {
        return try {
            val result = coinRepository.getCoin(id)
            Result.success(result)
        } catch (exception: Exception) {
            Timber.tag("Times Test").e(exception, "exception")
            Result.failure(exception)
        }
    }
}
