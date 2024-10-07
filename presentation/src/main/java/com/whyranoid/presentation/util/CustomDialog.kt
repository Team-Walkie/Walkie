package com.whyranoid.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun CustomDialog(
    title: String,
    description: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onAction,
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "확인",
                    style = WalkieTypography.SubTitle.copy(color = WalkieColor.Primary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            onAction()
                        }
                        .padding(bottom = 20.dp)
                        .padding(horizontal = 20.dp),
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title, style = WalkieTypography.SubTitle,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                style = WalkieTypography.Body1_Normal,
                textAlign = TextAlign.Center,
            )
        },
        modifier = modifier,
    )
}
