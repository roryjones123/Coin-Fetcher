package com.roz.coinfetcher.core.navigation

sealed class NavigationDestination(
    val route: String,
) {
    data object Coins : NavigationDestination("coinsDestination")
    data object Back : NavigationDestination("navigationBack")
}
