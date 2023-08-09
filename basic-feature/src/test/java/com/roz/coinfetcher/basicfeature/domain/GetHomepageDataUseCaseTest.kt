package com.roz.coinfetcher.basicfeature.domain

import app.cash.turbine.test
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.getHomepageData
import com.roz.coinfetcher.basicfeature.generateTestCoinsFromDomain
import com.roz.coinfetcher.basicfeature.generateTestTagsFromDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class GetHomepageDataUseCaseTest {

    @RelaxedMockK
    private lateinit var coinRepository: CoinRepository

    @RelaxedMockK
    private lateinit var tagRepository: TagRepository

    private lateinit var objectUnderTest: GetHomepageDataUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setupGetHomepageUseCase()
    }

    @Test
    fun `should wrap result with success if coin repository doesn't throw`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        every { coinRepository.getCoins() } returns flowOf(testCoinsFromDomain)
        every { tagRepository.getTags() } returns flowOf(testTagsFromDomain)

        // When-Then
        objectUnderTest.invoke().test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(testCoinsFromDomain),
                actual = result.map { it.first },
            )
            awaitComplete()
        }
    }

    @Test
    fun `should wrap result with success if tag repository doesn't throw`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        every { coinRepository.getCoins() } returns flowOf(testCoinsFromDomain)
        every { tagRepository.getTags() } returns flowOf(testTagsFromDomain)

        // When-Then
        objectUnderTest.invoke().test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(testTagsFromDomain),
                actual = result.map { it.second },
            )
            awaitComplete()
        }
    }

    @Test
    fun `should wrap result with failure if coin repository throws other Exception`() = runTest {
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

    @Test
    fun `should wrap result with failure if tag repository throws other Exception`() = runTest {
        // Given
        val testException = Exception("Test message")
        coEvery { tagRepository.getTags() } throws testException

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

    private fun setupGetHomepageUseCase() {
        objectUnderTest = GetHomepageDataUseCase {
            getHomepageData(coinRepository, tagRepository)
        }
    }
}
