package com.roz.coinfetcher.basicfeature.domain.repository

import com.roz.coinfetcher.basicfeature.domain.model.Tag

interface TagRepository {
    suspend fun getTags(): List<Tag>
}
