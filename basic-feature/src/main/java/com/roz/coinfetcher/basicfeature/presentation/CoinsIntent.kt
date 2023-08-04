package com.roz.coinfetcher.basicfeature.presentation

sealed class CoinsIntent {
    data object GetCoins : CoinsIntent()
    data object RefreshCoins : CoinsIntent()
    data class CoinClicked(val uri: String) : CoinsIntent()
}
