package com.roz.coinfetcher.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import com.roz.coinfetcher.basicfeature.domain.usecase.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.GetHomepageData
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.RefreshHomepageData
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.CoinClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.TagClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.DialogClosed
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Dialog
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Error
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Fetched
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Loading
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Filter
import com.roz.coinfetcher.basicfeature.presentation.mapper.toPresentationModel
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.basicfeature.presentation.utils.ApplyFilterUtil
import com.roz.coinfetcher.core.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val getHomepageDataUseCase: GetHomepageDataUseCase,
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle,
    coinsInitialState: HomepageUiState,
) : BaseViewModel<HomepageUiState, PartialState, HomepageIntent>(
    savedStateHandle,
    coinsInitialState,
) {
    init {
        observeContinuousChanges(getHomePageData())
    }

    override fun mapIntents(intent: HomepageIntent): Flow<PartialState> = when (intent) {
        is GetHomepageData -> getHomePageData()
        is RefreshHomepageData -> getHomePageData()
        is CoinClicked -> coinClicked(intent.id)
        is TagClicked -> tagClicked(intent.tagClicked)
        is DialogClosed -> closeDialog()
    }

    override fun reduceUiState(
        previousState: HomepageUiState,
        partialState: PartialState,
    ): HomepageUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is Filter -> {
            ApplyFilterUtil.applyFilters(previousState, partialState)
        }

        is Fetched -> previousState.copy(
            isLoading = false,
            coins = partialState.coinList,
            tags = partialState.tagList,
            isError = false,
        )

        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )

        is Dialog -> previousState.copy(
            complexCoin = partialState.complexCoin,
            isLoading = false,
            isError = false
        )
    }

    private fun getHomePageData(): Flow<PartialState> =
        getHomepageDataUseCase().map { response ->
            response.fold(
                onSuccess = { coinsAndTags ->
                    Fetched(
                        coinsAndTags.first.map { it.toPresentationModel() }.sortedBy { it.name },
                        coinsAndTags.second.map { it.toPresentationModel() })
                },
                onFailure = {
                    Error(it)
                }
            )
        }.onStart {
            emit(Loading)
        }

    private fun getCoin(id: String): Flow<PartialState> =
        getCoinUseCase(id = id).map { response ->
            response.fold(
                onSuccess = { coin ->
                    Dialog(coin.toPresentationModel().toString())
                },
                onFailure = {
                    Error(it)
                }
            )
        }.onStart {
            emit(Loading)
        }


    private fun coinClicked(id: String): Flow<PartialState> {
        return getCoin(id)
    }

    private fun tagClicked(tagClicked: TagDisplayable): Flow<PartialState> {
        return flow {
            emit(Filter(tagClicked))
        }
    }

    private fun closeDialog(): Flow<Dialog> {
        return flow {
            emit(Dialog(null))
        }
    }
}