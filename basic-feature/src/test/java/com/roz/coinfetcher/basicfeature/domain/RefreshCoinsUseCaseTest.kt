package com.roz.coinfetcher.basicfeature.domain

import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.RefreshCoinsUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.refreshCoins
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RefreshCoinsUseCaseTest {

    @RelaxedMockK
    private lateinit var coinRepository: CoinRepository

    private lateinit var objectUnderTest: RefreshCoinsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpRefreshRocketsUseCase()
    }

    @Test
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        coEvery { coinRepository.refreshCoins() } just Runs

        // When
        val result = objectUnderTest.invoke()

        // Then
        assertEquals(
            expected = Result.success(Unit),
            actual = result,
        )
    }

    @Test
    fun `should rethrow if repository throws CancellationException`() = runTest {
        // Given
        coEvery { coinRepository.refreshCoins() } throws CancellationException()

        // When-Then
        assertThrows<CancellationException> {
            objectUnderTest.invoke()
        }
    }

    @Test
    fun `should wrap result with failure if repository throws other Throwable`() = runTest {
        // Given
        val testException = Throwable("Test message")
        coEvery { coinRepository.refreshCoins() } throws testException

        // When-Then
        assertThrows<Throwable> {
            val result = objectUnderTest.invoke()

            assertEquals(
                expected = Result.failure(testException),
                actual = result,
            )
        }
    }

    private fun setUpRefreshRocketsUseCase() {
        objectUnderTest = RefreshCoinsUseCase {
            refreshCoins(coinRepository)
        }
    }
}
