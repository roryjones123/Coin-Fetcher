package com.roz.coinfetcher.basicfeature.data.repository

import com.roz.coinfetcher.basicfeature.data.mapper.toComplexDomainModel
import com.roz.coinfetcher.basicfeature.data.mapper.toDomainModel
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultCoinRepository @Inject constructor(
    private val coinApi: CoinApi
) : CoinRepository {
    override fun getCoins(): Flow<List<Coin>> = flow { emit(coinApi.getCoins().map { it.toDomainModel() }) }

    override fun getCoin(id: String): Flow<ComplexCoin> = flow { emit(coinApi.getCoin(id).toComplexDomainModel()) }
}
