package com.roz.coinfetcher.basicfeature.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagDisplayable(
    val name: String?,
    val id: String?,
    val taggedItems: List<String>? = emptyList(),
    val isSelected: Boolean = false,
    val numberOfTaggedItems: Long = 0L
) : Parcelable