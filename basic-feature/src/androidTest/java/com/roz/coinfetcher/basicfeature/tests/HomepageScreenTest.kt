package com.roz.coinfetcher.basicfeature.tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.basicfeature.data.generateTestCoinsFromPresentation
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomepageScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testCoins = generateTestCoinsFromPresentation()

    private lateinit var coinContentDescription: String
    private lateinit var errorRefreshingMessage: String
    private lateinit var errorFetchingMessage: String

    @Before
    fun setUp() {
        with(composeTestRule.activity) {
            coinContentDescription = getString(R.string.coin_type)
            errorRefreshingMessage = getString(R.string.coins_error_refreshing)
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
    fun homepageScreen_whenContentAvailableAndErrorOccurs_shouldShowErrorSnackbar() {
        setUpComposable(
            HomepageUiState(
                coins = testCoins,
                isError = true,
            ),
        )

        composeTestRule
            .onNodeWithText(errorRefreshingMessage)
            .assertExists()
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
