package com.roz.coinfetcher.basicfeature.domain

import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.getHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.generateTestCoinsFromDomain
import com.roz.coinfetcher.basicfeature.generateTestTagsFromDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        coEvery { coinRepository.getCoins() } returns testCoinsFromDomain
        coEvery { tagRepository.getTags() } returns testTagsFromDomain

        // When-Then
        val result = objectUnderTest.invoke()

        assertEquals(
            expected = Result.success(Pair(testCoinsFromDomain, testTagsFromDomain)),
            actual = result,
        )
    }

    @Test
    fun `should wrap result with failure if coin repository throws other Exception`() = runTest {
        // Given
        val testException = Exception("Test message")
        coEvery { coinRepository.getCoins() } throws testException

        // When-Then
        val result = objectUnderTest.invoke()

        assertEquals(
            expected = Result.failure(testException),
            actual = result
        )
    }

    @Test
    fun `should wrap result with failure if tag repository throws other Exception`() = runTest {
        // Given
        val testException = Exception("Test message")
        coEvery { tagRepository.getTags() } throws testException

        // When-Then
        val result = objectUnderTest.invoke()

        assertEquals(
            expected = Result.failure(testException),
            actual = result
        )
    }

    private fun setupGetHomepageUseCase() {
        objectUnderTest = GetHomepageDataUseCase {
            getHomepageDataUseCase(coinRepository, tagRepository)
        }
    }
}
