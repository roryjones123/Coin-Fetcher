package com.roz.coinfetcher.basicfeature.data.remote.api

import com.roz.coinfetcher.basicfeature.data.remote.model.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {
    @GET("coins")
    suspend fun getCoins(): List<CoinResponse>

    @GET("coins/{id}")
    suspend fun getCoinById(@Path("id") coinId: String): CoinResponse
}
