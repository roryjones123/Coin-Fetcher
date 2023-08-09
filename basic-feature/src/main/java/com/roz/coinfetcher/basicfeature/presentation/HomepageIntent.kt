package com.roz.coinfetcher.basicfeature.presentation

import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

sealed class HomepageIntent {
    data object GetHomepageData : HomepageIntent()
    data object RefreshHomepageData : HomepageIntent()
    data class CoinClicked(val id: String) : HomepageIntent()
    data class TagClicked(
        val tagClicked: TagDisplayable,
        val coins: List<CoinDisplayable>,
        val tags: List<TagDisplayable>
    ) : HomepageIntent()

    data object DialogClosed : HomepageIntent()
}
