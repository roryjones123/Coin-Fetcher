package com.roz.coinfetcher.basicfeature.domain.usecase

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.Tag
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import timber.log.Timber

fun interface GetHomepageDataUseCase : suspend () -> Result<Pair<List<Coin>, List<Tag>>>

suspend fun getHomepageDataUseCase(
    coinRepository: CoinRepository,
    tagRepository: TagRepository
): Result<Pair<List<Coin>, List<Tag>>> {
    try {
        val coins = coinRepository.getCoins().sortedBy { it.name }
        val tags = tagRepository.getTags()
        return Result.success(Pair(coins, tags))
    } catch (exception: Exception) {
        Timber.tag("Times Test").e(exception, "exception")
        return Result.failure(exception)
    }
}