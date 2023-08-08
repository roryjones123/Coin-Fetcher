package com.roz.coinfetcher.basicfeature.presentation.mapper

import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import com.roz.coinfetcher.basicfeature.presentation.model.ComplexCoinDisplayable

fun Coin.toPresentationModel() = CoinDisplayable(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isNew = isNew,
    isActive = isActive,
    type = type
)

fun ComplexCoin.toPresentationModel() = ComplexCoinDisplayable(
    id = id,
    name = name,
    symbol = symbol,
    parent = parent,
    rank = rank,
    isNew = isNew,
    isActive = isActive,
    type = type,
    logo = logo,
    tags = tags,
    team = team,
    description = description,
    message = message,
    openSource = openSource,
    hardwareWallet = hardwareWallet,
    startedAt = startedAt,
    developmentStatus = developmentStatus,
    proofType = proofType,
    orgStructure = orgStructure,
    hashAlgorithm = hashAlgorithm,
    contract = contract,
    platform = platform,
    contracts = contracts,
    links = links,
    linksExtended = linksExtended,
    whitepaper = whitepaper,
    firstDataAt = firstDataAt,
    lastDataAt = lastDataAt
)


