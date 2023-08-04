package com.roz.coinfetcher.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinsUseCase
import com.roz.coinfetcher.basicfeature.domain.usecase.RefreshCoinsUseCase
import com.roz.coinfetcher.basicfeature.presentation.CoinEvent.OpenWebBrowserWithDetails
import com.roz.coinfetcher.basicfeature.presentation.CoinsIntent.GetCoins
import com.roz.coinfetcher.basicfeature.presentation.CoinsIntent.RefreshCoins
import com.roz.coinfetcher.basicfeature.presentation.CoinsIntent.CoinClicked
import com.roz.coinfetcher.basicfeature.presentation.CoinsUiState.PartialState
import com.roz.coinfetcher.basicfeature.presentation.CoinsUiState.PartialState.Error
import com.roz.coinfetcher.basicfeature.presentation.CoinsUiState.PartialState.Fetched
import com.roz.coinfetcher.basicfeature.presentation.CoinsUiState.PartialState.Loading
import com.roz.coinfetcher.basicfeature.presentation.mapper.toPresentationModel
import com.roz.coinfetcher.core.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val HTTP_PREFIX = "http"
private const val HTTPS_PREFIX = "https"

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val refreshCoinsUseCase: RefreshCoinsUseCase,
    savedStateHandle: SavedStateHandle,
    coinsInitialState: CoinsUiState,
) : BaseViewModel<CoinsUiState, PartialState, CoinEvent, CoinsIntent>(
    savedStateHandle,
    coinsInitialState,
) {
    init {
        observeContinuousChanges(getCoins())
    }

    override fun mapIntents(intent: CoinsIntent): Flow<PartialState> = when (intent) {
        is GetCoins -> getCoins()
        is RefreshCoins -> refreshCoins()
        is CoinClicked -> coinClicked(intent.uri)
    }

    override fun reduceUiState(
        previousState: CoinsUiState,
        partialState: PartialState,
    ): CoinsUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is Fetched -> previousState.copy(
            isLoading = false,
            coins = partialState.list,
            isError = false,
        )

        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }

    private fun getCoins(): Flow<PartialState> =
        getCoinsUseCase()
            .map { result ->
                result.fold(
                    onSuccess = { coinList ->
                        Fetched(coinList.map { it.toPresentationModel() }.sortedBy { it.name })
                    },
                    onFailure = {
                        Error(it)
                    },
                )
            }
            .onStart {
                emit(Loading)
            }

    private fun refreshCoins(): Flow<PartialState> = flow {
        refreshCoinsUseCase()
            .onFailure {
                emit(Error(it))
            }
    }

    private fun coinClicked(uri: String): Flow<PartialState> {
        if (uri.startsWith(HTTP_PREFIX) || uri.startsWith(HTTPS_PREFIX)) {
            publishEvent(OpenWebBrowserWithDetails(uri))
        }

        return emptyFlow()
    }
}
