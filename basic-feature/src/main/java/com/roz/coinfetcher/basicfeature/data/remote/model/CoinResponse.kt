package com.roz.coinfetcher.basicfeature.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinResponse(
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

    @SerialName("tags")
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

@Serializable
data class Contract(
    val contract: String? = null,
    val platform: String? = null,
    val type: String? = null
)

@Serializable
data class Links(
    val explorer: List<String>? = null,
    val facebook: List<String>? = null,
    val reddit: List<String>? = null,

    @SerialName("source_code")
    val sourceCode: List<String>? = null,

    val website: List<String>? = null,
    val youtube: List<String>? = null,
    val medium: List<String>? = null
)

@Serializable
data class LinksExtended(
    val url: String? = null,
    val type: String? = null,
    val stats: Stats? = null
)

@Serializable
data class Stats(
    val subscribers: Long? = null,
    val contributors: Long? = null,
    val stars: Long? = null
)

@Serializable
data class Parent(
    val id: String? = null,
    val name: String? = null,
    val symbol: String? = null
)

@Serializable
data class Tag(
    val id: String? = null,
    val name: String? = null,

    @SerialName("coin_counter")
    val coinCounter: Long? = null,

    @SerialName("ico_counter")
    val icoCounter: Long? = null
)

@Serializable
data class Team(
    val id: String? = null,
    val name: String? = null,
    val position: String? = null
)

@Serializable
data class Whitepaper(
    val link: String? = null,
    val thumbnail: String? = null
)
