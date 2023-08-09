package com.roz.coinfetcher.basicfeature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.roz.coinfetcher.basicfeature.presentation.composable.HomepageRoute
import com.roz.coinfetcher.core.navigation.NavigationDestination.Homepage
import com.roz.coinfetcher.core.navigation.NavigationFactory
import javax.inject.Inject

class HomepageNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(Homepage.route) {
            HomepageRoute()
        }
    }
}
