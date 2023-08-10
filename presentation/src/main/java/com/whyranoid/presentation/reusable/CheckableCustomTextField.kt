package com.whyranoid.presentation.reusable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CheckableCustomTextField(
    text: String,
    onTextChanged: ((String) -> Unit)?,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    checkButton: (@Composable (String) -> Unit)? = null,
) {
    BasicTextField(
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        value = text,
        onValueChange = {
            onTextChanged?.invoke(it)
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Row(
                Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            placeholderText,
                            style = textStyle,
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null && checkButton == null) {
                    trailingIcon()
                } else if (trailingIcon != null && checkButton != null && text.isEmpty()) {
                    trailingIcon()
                } else if (checkButton != null && text.isEmpty().not()) checkButton(text)
            }
        },
    )
}
