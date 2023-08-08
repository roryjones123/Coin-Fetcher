package com.roz.coinfetcher.basicfeature.tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.basicfeature.data.generateTestCoinsFromPresentation
import com.roz.coinfetcher.basicfeature.data.generateTestTagsFromPresentation
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomepageScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testCoins = generateTestCoinsFromPresentation()
    private val testTags = generateTestTagsFromPresentation()

    private lateinit var coinContentDescription: String
    private lateinit var errorFetchingMessage: String
    private val testTag = "tag test tag"
    private val complexCoinText = "Hello I am a complex coin"

    @Before
    fun setUp() {
        with(composeTestRule.activity) {
            coinContentDescription = getString(R.string.coin_type)
            errorFetchingMessage = getString(R.string.coins_error_fetching)
        }
    }

    @Test
    fun homepageScreen_whenContentAvailableAndErrorOccurs_shouldKeepContent() {
        setUpComposable(
            HomepageUiState(
                coins = testCoins,
                isError = true,
            ),
        )

        composeTestRule
            .onAllNodesWithContentDescription(coinContentDescription)
            .assertCountEquals(testCoins.size)
    }

    @Test
    fun homepageScreen_whenComplexCoinSelected_shouldShowDialog() {
        setUpComposable(
            HomepageUiState(
                coins = testCoins,
                complexCoin = complexCoinText
            ),
        )

        composeTestRule
            .onNodeWithText(complexCoinText)
            .assertExists()
    }

    @Test
    fun homepageScreen_whenCoinsEmpty_shouldKeepTags() {
        setUpComposable(
            HomepageUiState(
                coins = emptyList(),
                tags = testTags,
                isLoading = false,
                isError = false
            ),
        )

        composeTestRule
            .onAllNodesWithTag(testTag)
            .assertCountEquals(testTags.size)
    }

    @Test
    fun homepageScreen_whenContentNotAvailableAndErrorOccurs_shouldHaveErrorContent() {
        setUpComposable(
            HomepageUiState(isError = true),
        )

        composeTestRule
            .onNodeWithText(errorFetchingMessage)
            .assertExists()
    }

    private fun setUpComposable(
        homepageUiState: HomepageUiState,
    ) {
        composeTestRule.setContent {
            CoinsScreen(
                uiState = homepageUiState,
                onIntent = { },
            )
        }
    }
}
