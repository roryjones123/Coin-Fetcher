package com.roz.coinfetcher.basicfeature.presentation

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class CoinsUiState(
    val isLoading: Boolean = false,
    val coins: List<CoinDisplayable> = emptyList(),
    val isError: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        data object Loading : PartialState() // for simplicity: initial loading & refreshing

        data class Fetched(val list: List<CoinDisplayable>) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}
