package com.roz.coinfetcher.basicfeature.presentation.mapper

import com.roz.coinfetcher.basicfeature.domain.model.Tag
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

fun Tag.toPresentationModel() = TagDisplayable(
    id = id,
    name = name,
    numberOfTaggedItems = numberOfTaggedItems,
    taggedItems = taggedItems
)