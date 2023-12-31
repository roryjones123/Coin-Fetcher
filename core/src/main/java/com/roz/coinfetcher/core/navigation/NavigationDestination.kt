package com.roz.coinfetcher.core.navigation

sealed class NavigationDestination(
    val route: String,
) {
    data object Homepage : NavigationDestination("homepageDestination")
    data object Back : NavigationDestination("navigationBack")
}
