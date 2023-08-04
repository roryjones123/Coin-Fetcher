package com.roz.coinfetcher.basicfeature.data.repository

import com.roz.coinfetcher.basicfeature.data.local.dao.CoinDao
import com.roz.coinfetcher.basicfeature.data.mapper.toDomainModel
import com.roz.coinfetcher.basicfeature.data.mapper.toEntityModel
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao,
) : CoinRepository {

    override fun getCoins(): Flow<List<Coin>> {
        return coinDao
            .getCoins()
            .map { coinsCached ->
                coinsCached.map { it.toDomainModel() }
            }
            .onEach { coins ->
                if (coins.isEmpty()) {
                    refreshCoins()
                }
            }
    }

    override suspend fun refreshCoins() {
        coinApi
            .getCoins()
            .map {
                it.toDomainModel().toEntityModel()
            }
            .also {
                coinDao.saveCoins(it)
            }
    }
}
