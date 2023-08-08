package com.roz.coinfetcher.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import coil.compose.AsyncImagePainter
import dagger.hilt.android.lifecycle.HiltViewModel
import com.roz.coinfetcher.basicfeature.domain.usecase.GetHomepageDataUseCase
import com.roz.coinfetcher.basicfeature.presentation.HomepageEvent.OpenWebBrowserWithDetails
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.GetHomepageData
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.RefreshHomepageData
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.CoinClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageIntent.TagClicked
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Error
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Fetched
import com.roz.coinfetcher.basicfeature.presentation.HomepageUiState.PartialState.Loading
import com.roz.coinfetcher.basicfeature.presentation.mapper.toPresentationModel
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.core.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val HTTP_PREFIX = "http"
private const val HTTPS_PREFIX = "https"

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val getHomepageDataUseCase: GetHomepageDataUseCase,
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
    }

    override fun reduceUiState(
        previousState: HomepageUiState,
        partialState: PartialState,
    ): HomepageUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is PartialState.Filter -> {
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

    private fun coinClicked(uri: String): Flow<PartialState> {
        if (uri.startsWith(HTTP_PREFIX) || uri.startsWith(HTTPS_PREFIX)) {
            publishEvent(OpenWebBrowserWithDetails(uri))
        }

        return emptyFlow()
    }

    private fun tagClicked(tagClicked: TagDisplayable): Flow<PartialState> {
        return flow {
            emit(PartialState.Filter(tagClicked))
        }
    }
}
