package com.roz.coinfetcher.basicfeature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String?,
    val id: String?,
    // icos and coins combined for simplicity
    val taggedItems: List<String>? = null,
    val numberOfTaggedItems: Long = 0L
)
