package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.basicfeature.presentation.CoinEvent
import com.roz.coinfetcher.basicfeature.presentation.CoinEvent.OpenWebBrowserWithDetails
import com.roz.coinfetcher.basicfeature.presentation.CoinsIntent
import com.roz.coinfetcher.basicfeature.presentation.CoinsIntent.RefreshCoins
import com.roz.coinfetcher.basicfeature.presentation.CoinsIntent.CoinClicked
import com.roz.coinfetcher.basicfeature.presentation.CoinsUiState
import com.roz.coinfetcher.basicfeature.presentation.CoinsViewModel
import com.roz.coinfetcher.core.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun CoinsRoute(
    viewModel: CoinsViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CoinsScreen(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )
}

@Composable
internal fun CoinsScreen(
    uiState: CoinsUiState,
    onIntent: (CoinsIntent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        // TODO: migrate from accompanist to built-in pull-to-refresh when added to Material3
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { onIntent(RefreshCoins) },
            modifier = Modifier
                .padding(it),
        ) {
            if (uiState.coins.isNotEmpty()) {
                CoinsAvailableContent(
                    snackbarHostState = snackbarHostState,
                    uiState = uiState,
                    onCoinClick = { onIntent(CoinClicked(it)) },
                )
            } else {
                CoinsNotAvailableContent(
                    uiState = uiState,
                )
            }
        }
    }
}

@Composable
private fun HandleEvents(events: Flow<CoinEvent>) {
    val uriHandler = LocalUriHandler.current

    events.collectWithLifecycle {
        when (it) {
            is OpenWebBrowserWithDetails -> {
                uriHandler.openUri(it.uri)
            }
        }
    }
}

@Composable
private fun CoinsAvailableContent(
    snackbarHostState: SnackbarHostState,
    uiState: CoinsUiState,
    onCoinClick: (String) -> Unit,
) {
    if (uiState.isError) {
        val errorMessage = stringResource(R.string.coins_error_refreshing)

        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
            )
        }
    }

    CoinsListContent(
        coinList = uiState.coins,
        onCoinClick = onCoinClick,
    )
}

@Composable
private fun CoinsNotAvailableContent(uiState: CoinsUiState) {
    when {
        uiState.isLoading -> CoinsLoadingPlaceholder()
        uiState.isError -> CoinsErrorContent()
    }
}
