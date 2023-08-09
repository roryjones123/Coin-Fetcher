package com.roz.coinfetcher.basicfeature.domain.repository

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(): Flow<List<Coin>>
    fun getCoin(id: String): Flow<ComplexCoin>
}
