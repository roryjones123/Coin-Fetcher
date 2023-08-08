package com.roz.coinfetcher.basicfeature.presentation

import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

sealed class HomepageIntent {
    data object GetHomepageData : HomepageIntent()
    data object RefreshHomepageData : HomepageIntent()
    data class CoinClicked(val uri: String) : HomepageIntent()
    data class TagClicked(val tagClicked: TagDisplayable) : HomepageIntent()

}
