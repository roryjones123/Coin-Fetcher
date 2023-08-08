package com.roz.coinfetcher.basicfeature.data.remote.api

import com.roz.coinfetcher.basicfeature.data.remote.model.TagResponse
import retrofit2.http.GET

interface TagApi {
    @GET("tags/")
    suspend fun getTags(): List<TagResponse>
}

