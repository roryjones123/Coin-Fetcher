package com.roz.coinfetcher.basicfeature.data.mapper

import com.roz.coinfetcher.basicfeature.data.remote.model.CoinResponse
import com.roz.coinfetcher.basicfeature.domain.model.Coin

fun CoinResponse.toDomainModel() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isNew = isNew,
    isActive = isActive,
    type = type
)