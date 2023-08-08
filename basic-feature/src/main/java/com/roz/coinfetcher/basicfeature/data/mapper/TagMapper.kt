package com.roz.coinfetcher.basicfeature.data.mapper

import com.roz.coinfetcher.basicfeature.data.remote.model.CoinResponse
import com.roz.coinfetcher.basicfeature.data.remote.model.TagResponse
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.Tag

fun TagResponse.toDomainModel() = Tag(name = name, id = id, coins = coins ?: emptyList())