package com.roz.coinfetcher.basicfeature.domain

import app.cash.turbine.test
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinsUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.getCoins
import com.roz.coinfetcher.basicfeature.generateTestRocketFromDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals

class GetCoinsUseCaseTest {

    @RelaxedMockK
    private lateinit var coinRepository: CoinRepository

    private lateinit var objectUnderTest: GetCoinsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpGetRocketsUseCase()
    }

    @Test
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        val testRocketsFromDomain = listOf(generateTestRocketFromDomain())
        coEvery { coinRepository.getCoins() } returns flowOf(testRocketsFromDomain)

        // When-Then
        objectUnderTest.invoke().test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(testRocketsFromDomain),
                actual = result,
            )
            awaitComplete()
        }
    }

    @Test
    fun `should retry operation if repository throws IOException`() = runTest {
        // Given
        val testException = IOException("Test message")
        val testRocketsFromDomain = listOf(generateTestRocketFromDomain())
        coEvery { coinRepository.getCoins() } throws testException andThen flowOf(testRocketsFromDomain)

        // When-Then
        assertThrows<IOException> {
            objectUnderTest.invoke().test {
                val errorResult = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = errorResult,
                )

                val itemsResult = awaitItem()

                assertEquals(
                    expected = Result.success(testRocketsFromDomain),
                    actual = itemsResult,
                )
            }
        }
    }

    @Test
    fun `should rethrow if repository throws CancellationException`() = runTest {
        // Given
        coEvery { coinRepository.getCoins() } throws CancellationException()

        // When-Then
        assertThrows<CancellationException> {
            objectUnderTest.invoke()
        }
    }

    @Test
    fun `should wrap result with failure if repository throws other Exception`() = runTest {
        // Given
        val testException = Exception("Test message")
        coEvery { coinRepository.getCoins() } throws testException

        // When-Then
        assertThrows<Exception> {
            objectUnderTest.invoke().test {
                val result = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = result,
                )
            }
        }
    }

    private fun setUpGetRocketsUseCase() {
        objectUnderTest = GetCoinsUseCase {
            getCoins(coinRepository)
        }
    }
}
