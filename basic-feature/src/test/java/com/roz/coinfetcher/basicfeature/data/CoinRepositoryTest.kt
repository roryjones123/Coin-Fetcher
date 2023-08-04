package com.roz.coinfetcher.basicfeature.data

import com.roz.coinfetcher.basicfeature.data.local.dao.CoinDao
import com.roz.coinfetcher.basicfeature.data.mapper.toDomainModel
import com.roz.coinfetcher.basicfeature.data.mapper.toEntityModel
import com.roz.coinfetcher.basicfeature.data.remote.api.CoinApi
import com.roz.coinfetcher.basicfeature.data.repository.CoinRepositoryImpl
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.generateTestRocketFromRemote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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

    @RelaxedMockK
    private lateinit var coinDao: CoinDao

    private lateinit var objectUnderTest: CoinRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpRocketRepository()
    }

    @Test
    fun `should refresh rockets if local database is empty`() = runTest {
        // Given
        every { coinDao.getCoins() } returns flowOf(emptyList())

        // When
        objectUnderTest.getCoins().collect()

        // Then
        coVerifyOrder {
            coinApi.getCoins()
            coinDao.saveCoins(any())
        }
    }

    @Test
    fun `should save mapped rockets locally if retrieved from remote`() = runTest {
        // Given
        val testRocketsFromRemote = listOf(generateTestRocketFromRemote())
        val testRocketsToCache = testRocketsFromRemote.map { it.toDomainModel().toEntityModel() }
        coEvery { coinApi.getCoins() } returns testRocketsFromRemote

        // When
        objectUnderTest.refreshCoins()

        // Then
        coVerify { coinDao.saveCoins(testRocketsToCache) }
    }

    private fun setUpRocketRepository() {
        objectUnderTest = CoinRepositoryImpl(
            coinApi,
            coinDao,
        )
    }
}
