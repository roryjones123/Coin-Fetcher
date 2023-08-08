package com.roz.coinfetcher.basicfeature.presentation.model

import android.os.Parcelable
import com.roz.coinfetcher.basicfeature.domain.model.Tag
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinDisplayable(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String
) : Parcelable

@Parcelize
data class TagDisplayable(
    val name: String?,
    val id: String?,
    val coins: List<String>? = emptyList(),
    val isSelected: Boolean = false
) : Parcelable
