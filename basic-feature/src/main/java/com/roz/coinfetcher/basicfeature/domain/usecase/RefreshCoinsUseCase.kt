package com.roz.coinfetcher.basicfeature.domain.usecase

import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.core.utils.resultOf

fun interface RefreshCoinsUseCase : suspend () -> Result<Unit>

suspend fun refreshCoins(
    coinRepository: CoinRepository,
): Result<Unit> = resultOf {
    coinRepository.refreshCoins()
}
