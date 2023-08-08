package com.roz.coinfetcher.basicfeature.domain.repository

import com.roz.coinfetcher.basicfeature.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun getTags(): List<Tag>
}
