package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.core.design.Typography

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    tag: TagDisplayable,
    onTagClick: (TagDisplayable) -> Unit,
) {
    val alpha = if (tag.isSelected) {
        PARTIAL_ALPHA
    } else {
        SOLID_ALPHA
    }

    Button(
        onClick = { onTagClick(tag) }, modifier = modifier
            .alpha(alpha)
            .testTag(TAG_TEST_TAG)
    ) {
        Text(text = "${tag.name} (${tag.numberOfTaggedItems})", style = Typography.bodySmall)
    }
}

private const val SOLID_ALPHA = 1f
private const val PARTIAL_ALPHA = 0.6f
private const val TAG_TEST_TAG = "tag test tag"