package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.window.Dialog
import com.roz.coinfetcher.basicfeature.R
import com.roz.coinfetcher.core.design.Typography

@Composable
fun ComplexCoinDialog(complexCoin: String, onDialogCloseClick: () -> Unit) {
    Dialog(onDismissRequest = { onDialogCloseClick() }) {
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(COIN_DIALOG_ROUNDNESS))
                .padding(dimensionResource(id = R.dimen.dimen_medium))
        ) {
            LazyColumn {
                // todo hacky workaround, is better way?
                items(1) {
                    IconButton(
                        onClick = { onDialogCloseClick() },
                    ) {
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "Close icon")
                    }

                    // todo properly parse coin and format this
                    Text(text = complexCoin, style = Typography.bodyMedium)
                }
            }
        }
    }
}

private const val COIN_DIALOG_ROUNDNESS = 5