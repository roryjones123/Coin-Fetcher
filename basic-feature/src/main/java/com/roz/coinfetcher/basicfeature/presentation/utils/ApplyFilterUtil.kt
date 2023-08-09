package com.roz.coinfetcher.basicfeature.presentation.utils

import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

// runs through the filtering process, filtering coins based on selected tags
object ApplyFilterUtil {
    fun applyFilters(
        previousState: HomepageUiState,
        partialState: HomepageUiState.PartialState.Filter
    ): HomepageUiState {
        val tags = mapTags(previousState.tags, partialState.tagPressed)
        val selectedTags = filterSelectedTags(tags)
        val coins = mapCoins(previousState.coins, selectedTags)

        return previousState.copy(tags = tags, coins = coins)
    }

    fun mapTags(tags: List<TagDisplayable>, tagPressed: TagDisplayable): List<TagDisplayable> {
        return tags.map { tag ->
            if (tag == tagPressed) {
                tag.copy(isSelected = !tag.isSelected)
            } else {
                tag
            }
        }.sortedByDescending { it.isSelected }
    }

    fun filterSelectedTags(tags: List<TagDisplayable>): List<TagDisplayable> {
        return tags.filter { it.isSelected }
    }

    fun mapCoins(coins: List<CoinDisplayable>, selectedTags: List<TagDisplayable>): List<CoinDisplayable> {
        return coins.map { coin ->
            val coinMatchesFilters = if (selectedTags.isEmpty()) {
                true
            } else {
                selectedTags.all { selectedTag ->
                    selectedTag.taggedItems?.contains(coin.id) == true
                }
            }
            coin.copy(isVisible = coinMatchesFilters)
        }
    }
}