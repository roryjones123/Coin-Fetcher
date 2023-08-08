package com.roz.coinfetcher.basicfeature.data

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.Tag
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

internal fun generateTestCoinsFromPresentation() = listOf(
    CoinDisplayable(
        id = "1",
        name = "Bitcoin",
        symbol = "BTC",
        rank = 1,
        isNew = false,
        isActive = true,
        type = "Cryptocurrency"
    ),
    CoinDisplayable(
        id = "2",
        name = "Ethereum",
        symbol = "ETH",
        rank = 2,
        isNew = false,
        isActive = true,
        type = "Cryptocurrency"
    ),
    CoinDisplayable(
        id = "3",
        name = "Cardano",
        symbol = "ADA",
        rank = 3,
        isNew = true,
        isActive = true,
        type = "Cryptocurrency"
    )
)

internal fun generateTestCoinsFromDomain() = listOf(
    Coin(
        id = "1",
        name = "Bitcoin",
        symbol = "BTC",
        rank = 1,
        isNew = false,
        isActive = true,
        type = "Cryptocurrency"
    ),
    Coin(
        id = "2",
        name = "Ethereum",
        symbol = "ETH",
        rank = 2,
        isNew = false,
        isActive = true,
        type = "Cryptocurrency"
    ),
    Coin(
        id = "3",
        name = "Cardano",
        symbol = "ADA",
        rank = 3,
        isNew = true,
        isActive = true,
        type = "Cryptocurrency"
    )
)

internal fun generateTestTagsFromDomain() = listOf(
    Tag(
        name = "Bigcoins",
        id = "1",
        coins = listOf("BTC", "ETH")
    ),
    Tag(
        name = "Ethereum",
        id = "2",
        coins = listOf("ETH", "ADA")
    ),
    Tag(
        name = "Altcoins",
        id = "3",
        coins = listOf("ADA", "XRP", "LTC")
    )
)

internal fun generateTestTagsFromPresentation() = listOf(
    TagDisplayable(
        name = "Bigcoins",
        id = "1",
        coins = listOf("BTC", "ETH")
    ),
    TagDisplayable(
        name = "Ethereum",
        id = "2",
        coins = listOf("ETH", "ADA")
    ),
    TagDisplayable(
        name = "Altcoins",
        id = "3",
        coins = listOf("ADA", "XRP", "LTC")
    )
)