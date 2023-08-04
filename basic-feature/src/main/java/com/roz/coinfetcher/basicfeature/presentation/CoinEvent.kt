package com.roz.coinfetcher.basicfeature.presentation

sealed class CoinEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : CoinEvent()
}
