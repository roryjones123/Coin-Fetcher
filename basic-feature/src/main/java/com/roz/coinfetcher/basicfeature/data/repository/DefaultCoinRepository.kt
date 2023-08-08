package com.roz.coinfetcher.basicfeature.data.repository

import com.roz.coinfetcher.basicfeature.data.mapper.toComplexDomainModel
import com.roz.coinfetcher.basicfeature.data.mapper.toDomainModel
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import javax.inject.Inject

class DefaultCoinRepository @Inject constructor(
    private val coinApi: CoinApi
) : CoinRepository {
    override suspend fun getCoins(): List<Coin> = coinApi.getCoins().map { it.toDomainModel() }

    override suspend fun getCoin(id: String): ComplexCoin {
        return coinApi.getCoin(id).toComplexDomainModel()
    }
}
