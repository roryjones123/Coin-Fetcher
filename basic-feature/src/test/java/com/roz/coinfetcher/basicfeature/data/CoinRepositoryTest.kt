package com.roz.coinfetcher.basicfeature.data

import com.roz.coinfetcher.basicfeature.data.local.dao.CoinDao
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.data.repository.DefaultCoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoinRepositoryTest {

    @RelaxedMockK
    private lateinit var coinApi: CoinApi

    private lateinit var objectUnderTest: CoinRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setupCoinRepository()
    }

    private fun setupCoinRepository() {
        objectUnderTest = DefaultCoinRepository(
            coinApi,
        )
    }
}
