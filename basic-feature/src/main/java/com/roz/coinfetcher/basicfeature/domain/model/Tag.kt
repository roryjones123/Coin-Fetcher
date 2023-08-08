package com.roz.coinfetcher.basicfeature.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Tag(
    val name: String?,
    val id: String?,

    @SerialName("coins")
    val coins: List<String>? = null
)
