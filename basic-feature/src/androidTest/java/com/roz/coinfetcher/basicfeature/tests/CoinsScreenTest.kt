package com.roz.coinfetcher.basicfeature.tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.basicfeature.data.generateTestRocketsFromPresentation
import com.roz.coinfetcher.basicfeature.presentation.CoinsUiState
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testRockets = generateTestRocketsFromPresentation()

    private lateinit var rocketContentDescription: String
    private lateinit var errorRefreshingMessage: String
    private lateinit var errorFetchingMessage: String

    @Before
    fun setUp() {
        with(composeTestRule.activity) {
            rocketContentDescription = getString(R.string.coin_type)
            errorRefreshingMessage = getString(R.string.coins_error_refreshing)
            errorFetchingMessage = getString(R.string.coins_error_fetching)
        }
    }

    @Test
    fun rocketsScreen_whenContentAvailableAndErrorOccurs_shouldKeepContent() {
        setUpComposable(
            CoinsUiState(
                coins = testRockets,
                isError = true,
            ),
        )

        composeTestRule
            .onAllNodesWithContentDescription(rocketContentDescription)
            .assertCountEquals(testRockets.size)
    }

    @Test
    fun rocketsScreen_whenContentAvailableAndErrorOccurs_shouldShowErrorSnackbar() {
        setUpComposable(
            CoinsUiState(
                coins = testRockets,
                isError = true,
            ),
        )

        composeTestRule
            .onNodeWithText(errorRefreshingMessage)
            .assertExists()
    }

    @Test
    fun rocketsScreen_whenContentNotAvailableAndErrorOccurs_shouldHaveErrorContent() {
        setUpComposable(
            CoinsUiState(isError = true),
        )

        composeTestRule
            .onNodeWithText(errorFetchingMessage)
            .assertExists()
    }

    private fun setUpComposable(
        coinsUiState: CoinsUiState,
    ) {
        composeTestRule.setContent {
            CoinsScreen(
                uiState = coinsUiState,
                onIntent = { },
            )
        }
    }
}
