package com.roz.coinfetcher.basicfeature.presentation.composable

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.roz.coinfetcher.basicfeature.presentation.model.TagDisplayable
import com.roz.coinfetcher.core.design.Purple40
import com.roz.coinfetcher.core.design.Typography

@Composable
fun TagItem(
    tag: TagDisplayable,
    modifier: Modifier = Modifier,
    onTagClick: (TagDisplayable) -> Unit,
) {
    val color = if (tag.isSelected) {
        Purple40.copy(alpha = 0.6f)
    } else {
        Purple40
    }
    Button(onClick = { onTagClick(tag) }, colors = ButtonDefaults.buttonColors(containerColor = color)) {
        Text(text = tag.name ?: "", style = Typography.titleMedium)
    }
}
