package com.roz.coinfetcher.basicfeature.presentation

sealed class HomepageEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : HomepageEvent()
}
