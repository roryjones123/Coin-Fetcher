package com.roz.coinfetcher.basicfeature.domain.repository

import com.roz.coinfetcher.basicfeature.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getTags(): Flow<List<Tag>>
}
