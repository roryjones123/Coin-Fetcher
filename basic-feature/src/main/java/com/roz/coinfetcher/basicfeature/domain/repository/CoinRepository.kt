package com.roz.coinfetcher.basicfeature.domain.repository

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(): Flow<List<Coin>>
    suspend fun refreshCoins()
}
