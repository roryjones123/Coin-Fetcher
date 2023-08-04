package com.roz.coinfetcher.basicfeature

import com.roz.coinfetcher.basicfeature.data.remote.model.CoinResponse
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import java.time.LocalDate

internal fun generateTestRocketFromRemote() = CoinResponse(
    id = "1",
    name = "test rocket",
    costPerLaunch = 10_000_000,
    firstFlightDate = "2022-09-10",
    wikiUrl = "https://testrocket.com",
    imageUrls = listOf("https://testrocket.com/1.jpg"),
)

internal fun generateTestRocketFromDomain() = Coin(
    id = "1",
    name = "test rocket",
    costPerLaunch = 10_000_000,
    firstFlight = LocalDate.parse("2022-09-10"),
    height = 20,
    weight = 30_000,
    wikiUrl = "https://testrocket.com",
    imageUrl = "https://testrocket.com/1.jpg",
)
