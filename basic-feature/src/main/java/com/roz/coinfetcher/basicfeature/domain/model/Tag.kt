package com.roz.coinfetcher.basicfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String?,
    val id: String?,
    val coins: List<String>? = null,
    val numberOfTaggedItems: Long = 0L
)
