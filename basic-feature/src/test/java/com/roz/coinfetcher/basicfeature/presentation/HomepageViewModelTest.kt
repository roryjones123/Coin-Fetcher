package com.roz.coinfetcher.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.roz.coinfetcher.basicfeature.domain.model.ComplexCoin
import com.roz.coinfetcher.basicfeature.domain.repository.CoinRepository
import com.roz.coinfetcher.basicfeature.domain.repository.TagRepository
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.getHomepageData
import com.roz.coinfetcher.basicfeature.generateTestCoinsFromDomain
import com.roz.coinfetcher.basicfeature.generateTestTagsFromDomain
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.RefreshHomepageData
import com.roz.coinfetcher.basicfeature.presentation.mapper.toPresentationModel
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.core.utils.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertContains
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
    fun `should show loading state with no error state first during init homepage retrieval`() = runTest {
        // Given
        every { getHomepageDataUseCase() } returns emptyFlow()
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
    fun `should show fetched coins with no loading & error state during init homepage retrieval success`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }

        every { getHomepageDataUseCase() } returns flowOf(
            Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        )
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
    fun `should show error state with no loading state during init homepage retrieval failure`() = runTest {
        // Given
        every { getHomepageDataUseCase() } returns flowOf(
            Result.failure(IllegalStateException("Test error")),
        )
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
    fun `should not show error state with previously fetched coins during homepage refresh failure`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }
        every { getHomepageDataUseCase() } returns flowOf(
            Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        )
        setUpHomepageViewModel()

        // When
        objectUnderTest.acceptIntent(RefreshHomepageData)

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertFalse(actualItem.isError)
            assertEquals(
                expected = testCoinsToPresentation,
                actual = actualItem.coins,
            )
            assertEquals(
                expected = testTagsToPresentation,
                actual = actualItem.tags,
            )
        }
    }

    @Test
    fun `should show appropriate coins when tags selected`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }
        every { getHomepageDataUseCase() } returns flowOf(
            Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        )
        setUpHomepageViewModel()
        val testTag = testTagsFromDomain.find { it.id == TEST_TAG_VALUE }?.toPresentationModel() ?: return@runTest

        // When
        objectUnderTest.acceptIntent(HomepageIntent.TagClicked(testTag, testCoinsToPresentation, testTagsToPresentation))

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            val tagsWithPressedTag = actualItem.coins.filter { it.id == TEST_TAG_VALUE }

            tagsWithPressedTag.forEach { button ->
                assertTrue(button.isVisible == true)
            }
        }
    }

    @Test
    fun `should hide appropriate coins when tags selected`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }
        every { getHomepageDataUseCase() } returns flowOf(
            Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        )
        setUpHomepageViewModel()
        val testTag = testTagsFromDomain.find { it.id == TEST_TAG_VALUE }?.toPresentationModel() ?: return@runTest

        // When
        objectUnderTest.acceptIntent(HomepageIntent.TagClicked(testTag, testCoinsToPresentation, testTagsToPresentation))

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            val tagsWithoutPressedTag = actualItem.coins.filterNot { it.id == TEST_TAG_VALUE }

            tagsWithoutPressedTag.forEach { button ->
                assertFalse(button.isVisible == true)
            }
        }
    }

    @Test
    fun `should show filter selected when tag pressed`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }
        every { getHomepageDataUseCase() } returns flowOf(
            Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        )
        setUpHomepageViewModel()
        val testTag = testTagsFromDomain.find { it.id == TEST_TAG_VALUE }?.toPresentationModel() ?: return@runTest

        // When
        objectUnderTest.acceptIntent(HomepageIntent.TagClicked(testTag, testCoinsToPresentation, testTagsToPresentation))

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            val selectedTag = actualItem.tags.find { it.id == TEST_TAG_VALUE }
            assertTrue(selectedTag?.isSelected == true)
        }
    }

    @Test
    fun `should show filter selected when tag pressed twice`() = runTest {
        // Given
        val testCoinsFromDomain = generateTestCoinsFromDomain()
        val testTagsFromDomain = generateTestTagsFromDomain()
        val testCoinsToPresentation = testCoinsFromDomain.map { it.toPresentationModel() }
        val testTagsToPresentation = testTagsFromDomain.map { it.toPresentationModel() }
        every { getHomepageDataUseCase() } returns flowOf(
            Result.success(Pair(testCoinsFromDomain, testTagsFromDomain))
        )
        setUpHomepageViewModel()
        val testTag = testTagsFromDomain.find { it.id == TEST_TAG_VALUE }?.toPresentationModel() ?: return@runTest

        // When
        objectUnderTest.acceptIntent(HomepageIntent.TagClicked(testTag, testCoinsToPresentation, testTagsToPresentation))
        objectUnderTest.acceptIntent(HomepageIntent.TagClicked(testTag, testCoinsToPresentation, testTagsToPresentation))

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            val selectedTag = actualItem.tags.find { it.id == TEST_TAG_VALUE }
            assertFalse(selectedTag?.isSelected == true)
        }
    }

    /* @Test
    fun `complex coin value should not be null when dialog clicked`() = runTest {
        // Given
        val firstCoin = generateTestCoinsFromDomain().first()
        val dummyComplexCoin = ComplexCoin(firstCoin.id, firstCoin.name, "Test", 5, true, true, "Test")
        every { getCoinUseCase.invoke(firstCoin.id) } returns flowOf(
            Result.success(dummyComplexCoin)
        )

        setUpHomepageViewModel()

        // When
        objectUnderTest.acceptIntent(HomepageIntent.CoinClicked(firstCoin.id))

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            actualItem.complexCoin.toString()

            assertEquals(dummyComplexCoin.toString(), actualItem.complexCoin)
        }
    }*/

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

private const val TEST_TAG_VALUE = "ADA"