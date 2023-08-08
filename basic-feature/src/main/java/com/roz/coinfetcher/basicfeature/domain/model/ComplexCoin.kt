package com.roz.coinfetcher.basicfeature.domain.model

import com.roz.coinfetcher.basicfeature.data.remote.model.Contract
import com.roz.coinfetcher.basicfeature.data.remote.model.Links
import com.roz.coinfetcher.basicfeature.data.remote.model.LinksExtended
import com.roz.coinfetcher.basicfeature.data.remote.model.Parent
import com.roz.coinfetcher.basicfeature.data.remote.model.Team
import com.roz.coinfetcher.basicfeature.data.remote.model.Whitepaper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComplexCoin(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String,
    val logo: String? = null,
    val description: String? = null,
    val message: String? = null,
    val openSource: Boolean? = null,
    val hardwareWallet: Boolean? = null,
    val startedAt: String? = null,
    val developmentStatus: String? = null,
    val proofType: String? = null,
    val orgStructure: String? = null,
    val hashAlgorithm: String? = null,
    val contract: String? = null,
    val platform: String? = null,
    val firstDataAt: String? = null,
    val lastDataAt: String? = null
)