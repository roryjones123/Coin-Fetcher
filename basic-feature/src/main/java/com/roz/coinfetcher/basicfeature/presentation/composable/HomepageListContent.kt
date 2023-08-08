package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

@Composable
fun CoinsListContent(
    coinList: List<CoinDisplayable>,
    tagList: List<TagDisplayable>,
    modifier: Modifier = Modifier,
    onCoinClick: (String) -> Unit,
    onTagClick: (TagDisplayable) -> Unit,
) {
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimen_small)),
        ) {
            itemsIndexed(
                items = tagList,
            ) { _, tag ->
                TagItem(tag = tag, onTagClick = { onTagClick(tag) })
            }
        }
        LazyColumn(
            modifier = modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimen_medium),
                ),
        ) {
            itemsIndexed(
                items = coinList,
                key = { _, coin -> coin.id },
            ) { index, item ->
                CoinItem(
                    coin = item,
                    onCoinClick = { onCoinClick(item.id) },
                )

                if (index < coinList.lastIndex) {
                    Divider(
                        modifier = Modifier.testTag(COINS_DIVIDER_TEST_TAG),
                    )
                }
            }
        }
    }
}

const val COINS_DIVIDER_TEST_TAG = "coinsDividerTestTag"
