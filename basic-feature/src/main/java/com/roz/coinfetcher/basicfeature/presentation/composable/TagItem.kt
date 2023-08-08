package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.core.design.Typography

@Composable
fun TagItem(
    tag: TagDisplayable,
    onTagClick: (TagDisplayable) -> Unit,
) {
    val alpha = if (tag.isSelected) {
        PARTIAL_ALPHA
    } else {
        SOLID_ALPHA
    }

    Button(onClick = { onTagClick(tag) }, modifier = Modifier.alpha(alpha)) {
        Text(text = tag.name ?: "", style = Typography.titleMedium)
    }
}

private const val SOLID_ALPHA = 1f
private const val PARTIAL_ALPHA = 0.6f