package com.roz.coinfetcher.basicfeature.presentation.mapper

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable

fun Coin.toPresentationModel() = CoinDisplayable(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isNew = isNew,
    isActive = isActive,
    type = type,
)

