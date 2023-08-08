package com.roz.coinfetcher.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.generateTestCoinsFromDomain
import com.roz.coinfetcher.basicfeature.generateTestTagsFromDomain
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.RefreshHomepageData
import com.roz.coinfetcher.basicfeature.presentation.mapper.toPresentationModel
import com.roz.coinfetcher.core.utils.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HomepageViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @RelaxedMockK
    private lateinit var getHomepageDataUseCase: GetHomepageDataUseCase

    @RelaxedMockK
    private lateinit var getCoinUseCase: GetCoinUseCase


    @SpyK
    private var savedStateHandle = SavedStateHandle()

    private lateinit var objectUnderTest: HomepageViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should show loading state with no error state first during init coins retrieval`() = runTest {
        // Given
        coEvery { getHomepageDataUseCase() } returns Result.success(Pair(listOf(), listOf()))
        setUpHomepageViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show fetched coins with no loading & error state during init coins retrieval success`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }

        coEvery { getHomepageDataUseCase() } returns Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        setUpHomepageViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertEquals(
                expected = testCoinsToPresentation,
                actual = actualItem.coins,
            )

            assertEquals(
                expected = testTagsToPresentation,
                actual = actualItem.tags,
            )
            assertFalse(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show error state with no loading state during init coins retrieval failure`() = runTest {
        // Given
        coEvery { getHomepageDataUseCase() } returns Result.failure(IllegalStateException("Test error"))
        setUpHomepageViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isError)
            assertFalse(actualItem.isLoading)
        }
    }

    @Test
    fun `should show error state with previously fetched coins during coins refresh failure`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        coEvery { getHomepageDataUseCase() } returns
                Result.success(
                    Pair(testCoinsFromDomain, testTagsFromDomain),
                )
        setUpHomepageViewModel()

        // When
        objectUnderTest.acceptIntent(RefreshHomepageData)

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isError)
            assertEquals(
                expected = testCoinsToPresentation,
                actual = actualItem.coins,
            )
        }
    }

    private fun setUpHomepageViewModel(
        initialUiState: HomepageUiState = HomepageUiState(),
    ) {
        objectUnderTest = HomepageViewModel(
            getHomepageDataUseCase,
            getCoinUseCase,
            savedStateHandle,
            initialUiState,
        )
    }
}
