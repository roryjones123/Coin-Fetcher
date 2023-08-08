package com.roz.coinfetcher.basicfeature.data.mapper

import com.roz.coinfetcher.basicfeature.data.remote.model.CoinResponse
import com.roz.coinfetcher.basicfeature.domain.model.Coin
import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.domain.model.Tag

fun CoinResponse.toDomainModel() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isNew = isNew,
    isActive = isActive,
    type = type
)


fun CoinResponse.toComplexDomainModel() = ComplexCoin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isNew = isNew,
    isActive = isActive,
    type = type,
    logo = logo,
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
    firstDataAt = firstDataAt,
    lastDataAt = lastDataAt
)