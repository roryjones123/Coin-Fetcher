package com.roz.coinfetcher.basicfeature.presentation.model

import kotlinx.serialization.Serializable

// todo re-add objects in complex coin, create proper layered versions of objects (like Whitepaper etc)
@Serializable
data class ComplexCoinDisplayable(
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
) {

    // todo properly parse data to dialog and format in Compose
    override fun toString(): String {
        return buildString {
            appendLine("ComplexCoinDisplayable:")
            appendNotNullLine("id", id)
            appendNotNullLine("name", name)
            appendNotNullLine("symbol", symbol)
            appendNotNullLine("rank", rank)
            appendNotNullLine("isNew", isNew)
            appendNotNullLine("isActive", isActive)
            appendNotNullLine("type", type)
            appendNotNullLine("logo", logo)
            appendNotNullLine("description", description)
            appendNotNullLine("message", message)
            appendNotNullLine("openSource", openSource)
            appendNotNullLine("hardwareWallet", hardwareWallet)
            appendNotNullLine("startedAt", startedAt)
            appendNotNullLine("developmentStatus", developmentStatus)
            appendNotNullLine("proofType", proofType)
            appendNotNullLine("orgStructure", orgStructure)
            appendNotNullLine("hashAlgorithm", hashAlgorithm)
            appendNotNullLine("contract", contract)
            appendNotNullLine("platform", platform)
            appendNotNullLine("firstDataAt", firstDataAt)
            appendNotNullLine("lastDataAt", lastDataAt)
        }
    }

    private fun StringBuilder.appendNotNullLine(propertyName: String, value: Any?) {
        if (value != null && value.toString().isNotEmpty()) {
            appendLine("$propertyName: $value")
        }
    }
}