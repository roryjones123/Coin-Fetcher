package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.basicfeature.presentation.model.CoinDisplayable
import com.roz.coinfetcher.core.design.Typography

@Composable
fun CoinItem(
    coin: CoinDisplayable,
    modifier: Modifier = Modifier,
    onCoinClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.dimen_medium),
            )
            .clickable { onCoinClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.dimen_small),
            ),
        ) {
            Text(
                text = coin.name,
                style = Typography.titleMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.coin_symbol,
                    coin.symbol,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.coin_type,
                    coin.type,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.coin_is_active,
                    coin.isActive,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.coin_is_new,
                    coin.isNew,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.coin_rank,
                    coin.rank,
                ),
                style = Typography.bodyMedium,
            )
        }
    }
}
