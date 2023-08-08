package com.roz.coinfetcher.basicfeature.tests

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.roz.coinfetcher.basicfeature.data.generateTestCoinsFromPresentation
import com.roz.coinfetcher.basicfeature.data.generateTestTagsFromPresentation
import com.roz.coinfetcher.basicfeature.presentation.composable.COINS_DIVIDER_TEST_TAG
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsListContent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomepageListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testCoins = generateTestCoinsFromPresentation()
    private val testTags = generateTestTagsFromPresentation()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CoinsListContent(
                coinList = testCoins,
                onTagClick = { },
                tagList = testTags,
                onCoinClick = {}
            )
        }
    }

    @Test
    fun coinsListContent_shouldNotShowTheDividerAfterLastItem() {
        val expectedDividerCount = testCoins.size - 1

        composeTestRule
            .onAllNodesWithTag(COINS_DIVIDER_TEST_TAG)
            .assertCountEquals(expectedDividerCount)
    }
}
