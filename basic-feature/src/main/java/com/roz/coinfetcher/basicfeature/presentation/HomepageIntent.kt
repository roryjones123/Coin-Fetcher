package com.roz.coinfetcher.basicfeature.presentation

import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

sealed class HomepageIntent {
    data object GetHomepageData : HomepageIntent()
    data object RefreshHomepageData : HomepageIntent()
    data class CoinClicked(val id: String) : HomepageIntent()
    data class TagClicked(val tagClicked: TagDisplayable) : HomepageIntent()
    data object DialogClosed : HomepageIntent()
}
