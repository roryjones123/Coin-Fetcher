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
import com.roz.coinfetcher.basicfeature.presentation.HomepageEvent
import com.roz.coinfetcher.basicfeature.presentation.HomepageEvent.OpenWebBrowserWithDetails
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.RefreshHomepageData
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.CoinClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState
import com.roz.coinfetcher.basicfeature.presentation.HomepageViewModel
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.core.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun HomepageRoute(
    viewModel: HomepageViewModel = hiltViewModel(),
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
    uiState: HomepageUiState,
    onIntent: (HomepageIntent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        // TODO: migrate from accompanist to built-in pull-to-refresh when added to Material3
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { onIntent(RefreshHomepageData) },
            modifier = Modifier
                .padding(it),
        ) {
            if (uiState.coins.isNotEmpty()) {
                CoinsAvailableContent(
                    snackbarHostState = snackbarHostState,
                    uiState = uiState,
                    onCoinClick = { onIntent(CoinClicked(it)) },
                    onTagClick = { onIntent(HomepageIntent.TagClicked(it)) }
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
private fun HandleEvents(events: Flow<HomepageEvent>) {
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
    uiState: HomepageUiState,
    onCoinClick: (String) -> Unit,
    onTagClick: (TagDisplayable) -> Unit
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
        tagList = uiState.tags,
        onCoinClick = onCoinClick,
        onTagClick = onTagClick
    )
}

@Composable
private fun CoinsNotAvailableContent(uiState: HomepageUiState) {
    when {
        uiState.isLoading -> CoinsLoadingPlaceholder()
        uiState.isError -> CoinsErrorContent()
    }
}
