package com.whyranoid.presentation.component.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    maxLines: Int = 2
) {
    var isExpanded by remember { mutableStateOf(false) }
    var needsButton by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }

    Column {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    needsButton = true
                }
                textLayoutResultState.value = textLayoutResult
            }
        )

        if (needsButton) {
            TextButton(
                onClick = { isExpanded = isExpanded.not() }
            ) {
                Text(if (isExpanded.not()) "더보기" else "접어두기", color = WalkieColor.GrayDefault)
            }
        }
    }
}

@Composable
@Preview
fun a() {
    ExpandableText(text = "post.contents\npost.contents\npost.contents\npost.contents")
}