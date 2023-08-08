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
import com.roz.coinfetcher.core.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val getHomepageDataUseCase: GetHomepageDataUseCase,
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle,
    coinsInitialState: HomepageUiState,
) : BaseViewModel<HomepageUiState, PartialState, HomepageEvent, HomepageIntent>(
    savedStateHandle,
    coinsInitialState,
) {
    init {
        observeContinuousChanges(getHomePageData())
    }

    override fun mapIntents(intent: HomepageIntent): Flow<PartialState> = when (intent) {
        is GetHomepageData -> getHomePageData()
        is RefreshHomepageData -> getHomePageData()
        is CoinClicked -> coinClicked(intent.uri)
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
            val tags = previousState.tags.map { tag ->
                if (tag == partialState.tagPressed) {
                    tag.copy(isSelected = !tag.isSelected)
                } else {
                    tag
                }
            }.sortedBy { !it.isSelected }

            previousState.copy(
                tags = tags,
                /*coins = previousState.coins.filter { coin ->
                    tags.any { tag ->
                        tag.coins?.contains(coin.name) == true
                    }
                }*/
            )
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
            complexCoin = partialState.complexCoin
        )
    }

    private fun getHomePageData(): Flow<PartialState> = flow {
        getHomepageDataUseCase().onSuccess {
            emit(
                Fetched(
                    coinList = it.first.map { it.toPresentationModel() },
                    tagList = it.second.map { it.toPresentationModel() })
            )
        }.onFailure {
            emit(Error(it))
        }
    }.onStart { Loading }


    private fun getCoin(id: String): Flow<PartialState> = flow {
        getCoinUseCase(id = id)
            .onSuccess { complexCoin ->
                emit(Dialog(complexCoin.toPresentationModel().toString()))
            }.onFailure {
                emit(Error(it))
            }
    }.onStart { emit(Loading) }

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
