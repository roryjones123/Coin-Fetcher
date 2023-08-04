package com.roz.coinfetcher.basicfeature.tests

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.roz.coinfetcher.basicfeature.data.generateTestRocketsFromDomain
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsRoute
import com.roz.coinfetcher.core.MainActivity
import com.roz.coinfetcher.core.utils.getHiltTestViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CoinsRouteTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val testRockets = generateTestRocketsFromDomain()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            CoinsRoute(
                viewModel = composeTestRule.getHiltTestViewModel(),
            )
        }
    }

    @Test
    fun rocketsRoute_whenHappyPath_shouldShowAllFakeRockets() {
        testRockets.forEach { rocket ->
            composeTestRule
                .onNodeWithText(rocket.name)
                .assertExists()
        }
    }
}
