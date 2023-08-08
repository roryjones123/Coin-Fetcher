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
    val parent: Parent? = null,
    val rank: Int,

    @SerialName("is_new")
    val isNew: Boolean,

    @SerialName("is_active")
    val isActive: Boolean,

    val type: String,
    val logo: String? = null,
    val tags: List<Tag>? = null,
    val team: List<Team>? = null,
    val description: String? = null,
    val message: String? = null,

    @SerialName("open_source")
    val openSource: Boolean? = null,

    @SerialName("hardware_wallet")
    val hardwareWallet: Boolean? = null,

    @SerialName("started_at")
    val startedAt: String? = null,

    @SerialName("development_status")
    val developmentStatus: String? = null,

    @SerialName("proof_type")
    val proofType: String? = null,

    @SerialName("org_structure")
    val orgStructure: String? = null,

    @SerialName("hash_algorithm")
    val hashAlgorithm: String? = null,

    val contract: String? = null,
    val platform: String? = null,
    val contracts: List<Contract>? = null,
    val links: Links? = null,

    @SerialName("links_extended")
    val linksExtended: List<LinksExtended>? = null,

    val whitepaper: Whitepaper? = null,

    @SerialName("first_data_at")
    val firstDataAt: String? = null,

    @SerialName("last_data_at")
    val lastDataAt: String? = null
)