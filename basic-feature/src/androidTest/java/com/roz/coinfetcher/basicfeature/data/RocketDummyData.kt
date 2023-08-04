package com.roz.coinfetcher.basicfeature.data

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import java.time.LocalDate

internal fun generateTestRocketsFromPresentation() = listOf(
    CoinDisplayable(
        id = "1",
        name = "test rocket",
        costPerLaunchInMillions = 10,
        firstFlightDate = "2022-09-25",
        heightInMeters = 20,
        weightInTonnes = 30,
        wikiUrl = "https://testrocket.com",
        imageUrl = "",
    ),
    CoinDisplayable(
        id = "2",
        name = "test rocket 2",
        costPerLaunchInMillions = 20,
        firstFlightDate = "2022-09-25",
        heightInMeters = 40,
        weightInTonnes = 50,
        wikiUrl = "https://testrocket.com",
        imageUrl = "",
    ),
    CoinDisplayable(
        id = "3",
        name = "test rocket 3",
        costPerLaunchInMillions = 30,
        firstFlightDate = "2022-09-25",
        heightInMeters = 60,
        weightInTonnes = 70,
        wikiUrl = "https://testrocket.com",
        imageUrl = "",
    ),
)

internal fun generateTestRocketsFromDomain() = listOf(
    Coin(
        id = "1",
        name = "test rocket",
        costPerLaunch = 10_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 20,
        weight = 30_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/1.jpg",
    ),
    Coin(
        id = "2",
        name = "test rocket 2",
        costPerLaunch = 20_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 40,
        weight = 50_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/2.jpg",
    ),
    Coin(
        id = "3",
        name = "test rocket 3",
        costPerLaunch = 30_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 60,
        weight = 70_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/3.jpg",
    ),
)
