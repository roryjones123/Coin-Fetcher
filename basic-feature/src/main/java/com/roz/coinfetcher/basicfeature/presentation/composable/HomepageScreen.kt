package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.TagClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.DialogClosed
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.RefreshHomepageData
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.CoinClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState
import com.roz.coinfetcher.basicfeature.presentation.HomepageViewModel
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable

@Composable
fun HomepageRoute(
    viewModel: HomepageViewModel = hiltViewModel(),
) {
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
        floatingActionButton = {
            Button(onClick = { onIntent(RefreshHomepageData) })
            {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Refresh Icon"
                )
            }
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                if (uiState.isLoading || uiState.isError) {
                    CoinsNotAvailableContent(
                        uiState = uiState,
                    )
                } else if (uiState.coins.isNotEmpty() || uiState.tags.isNotEmpty()) {
                    CoinsAvailableContent(
                        uiState = uiState,
                        onCoinClick = { onIntent(CoinClicked(it)) },
                        onTagClick = { onIntent(TagClicked(it)) }
                    )
                }

                if (uiState.complexCoin != null) {
                    ComplexCoinDialog(complexCoin = uiState.complexCoin, onDialogCloseClick = { onIntent(DialogClosed) })
                }
            }
        })
}

@Composable
private fun CoinsAvailableContent(
    uiState: HomepageUiState,
    onCoinClick: (String) -> Unit,
    onTagClick: (TagDisplayable) -> Unit
) {
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
        uiState.isLoading -> HomepageLoadingIndicator()
        uiState.isError -> HomepageErrorContent()
    }
}