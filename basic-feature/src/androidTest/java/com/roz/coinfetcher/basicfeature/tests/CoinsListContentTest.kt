package com.roz.coinfetcher.basicfeature.tests

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.roz.coinfetcher.basicfeature.data.generateTestRocketsFromPresentation
import com.roz.coinfetcher.basicfeature.presentation.composable.COINS_DIVIDER_TEST_TAG
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsListContent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinsListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testRockets = generateTestRocketsFromPresentation()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CoinsListContent(
                coinList = testRockets,
                onCoinClick = { },
            )
        }
    }

    @Test
    fun rocketsListContent_shouldNotShowTheDividerAfterLastItem() {
        val expectedDividerCount = testRockets.size - 1

        composeTestRule
            .onAllNodesWithTag(COINS_DIVIDER_TEST_TAG)
            .assertCountEquals(expectedDividerCount)
    }
}
