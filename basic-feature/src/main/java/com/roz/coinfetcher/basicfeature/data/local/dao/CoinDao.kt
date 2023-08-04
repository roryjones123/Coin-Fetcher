package com.roz.coinfetcher.basicfeature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.roz.coinfetcher.basicfeature.data.local.model.CoinCached
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT * FROM CoinCached")
    fun getCoins(): Flow<List<CoinCached>>

    @Upsert
    suspend fun saveCoins(coins: List<CoinCached>)
}
