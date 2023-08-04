package com.roz.coinfetcher.basicfeature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.roz.coinfetcher.basicfeature.presentation.composable.CoinsRoute
import com.roz.coinfetcher.core.navigation.NavigationDestination.Coins
import com.roz.coinfetcher.core.navigation.NavigationFactory
import javax.inject.Inject

class CoinsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(Coins.route) {
            CoinsRoute()
        }
    }
}
