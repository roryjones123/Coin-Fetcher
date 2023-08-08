package com.roz.coinfetcher.basicfeature.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagResponse(
    val id: String,
    val name: String,
    @SerialName("coin_counter")
    val coinCounter: Long,
    @SerialName("ico_counter")
    val icoCounter: Long,
    val description: String,
    val type: String,
    @SerialName("coins")
    val coins: List<String>? = null,
    @SerialName("icos")
    val icos: List<String>? = null
)
