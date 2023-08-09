package com.roz.coinfetcher.basicfeature

import com.roz.coinfetcher.basicfeature.data.remote.model.CoinResponse
import com.roz.coinfetcher.basicfeature.data.remote.model.Links
import com.roz.coinfetcher.basicfeature.data.remote.model.Whitepaper
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.Tag

internal fun generateTestCoinFromRemote() = CoinResponse(
    id = "3",
    name = "Cardano",
    symbol = "ADA",
    rank = 3,
    isNew = true,
    isActive = true,
    type = "Cryptocurrency",
    logo = "https://example.com/cardano_logo.png",
    description = "Cardano is a blockchain platform for smart contracts and dApps.",
    startedAt = "2017-09-29",
    developmentStatus = "Active",
    proofType = "Proof of Stake",
    orgStructure = "Decentralized",
    hashAlgorithm = "Ouroboros",
    contract = "0xCardanoSmartContract",
    platform = "Cardano",
    links = Links(
        explorer = listOf("https://cardanoexplorer.com"),
        website = listOf("https://cardano.org"),
        reddit = listOf("https://reddit.com/r/cardano"),
        sourceCode = listOf("https://github.com/input-output-hk/cardano-node"),
        medium = listOf("https://medium.com/cardano-foundation")
    ),
    whitepaper = Whitepaper(
        link = "https://cardano.org/whitepaper",
        thumbnail = "https://example.com/cardano_whitepaper_thumbnail.png"
    )
)

internal fun generateTestCoinsFromDomain() = listOf(
    Coin(
        id = "ADA",
        name = "Cardano",
        symbol = "ADA",
        rank = 1,
        isNew = false,
        isActive = true,
        type = "Cryptocurrency"
    )
)

internal fun generateTestTagsFromDomain() = listOf(
    Tag(
        name = "Bigcoins",
        id = "1",
        taggedItems = listOf("BTC", "ETH")
    ),
    Tag(
        name = "Ethereum",
        id = "2",
        taggedItems = listOf("ETH", "ADA")
    ),
    Tag(
        name = "Altcoins",
        id = "3",
        taggedItems = listOf("ADA", "XRP", "LTC")
    )
)