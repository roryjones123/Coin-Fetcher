package com.roz.coinfetcher.basicfeature.data.repository

import com.roz.coinfetcher.basicfeature.data.mapper.toDomainModel
import com.roz.coinfetcher.basicfeature.data.remote.api.TagApi
import com.roz.coinfetcher.basicfeature.domain.model.Tag
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultTagRepository @Inject constructor(
    private val tagApi: TagApi
) : TagRepository {
    override fun getTags(): Flow<List<Tag>> = flow { emit(tagApi.getTags().map { it.toDomainModel() }) }
}
