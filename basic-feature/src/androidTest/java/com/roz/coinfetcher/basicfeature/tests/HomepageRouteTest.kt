package com.roz.coinfetcher.basicfeature.tests

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.roz.coinfetcher.basicfeature.data.generateTestCoinsFromDomain
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.roz.coinfetcher.basicfeature.presentation.composable.HomepageRoute
import com.roz.coinfetcher.core.MainActivity
import com.roz.coinfetcher.core.utils.getHiltTestViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomepageRouteTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val testCoins = generateTestCoinsFromDomain()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            HomepageRoute(
                viewModel = composeTestRule.getHiltTestViewModel(),
            )
        }
    }

    @Test
    fun homepageRoute_whenHappyPath_shouldShowAllFakeCoins() {
        testCoins.forEach { coin ->
            composeTestRule
                .onNodeWithText(coin.name)
                .assertExists()
        }
    }
}
