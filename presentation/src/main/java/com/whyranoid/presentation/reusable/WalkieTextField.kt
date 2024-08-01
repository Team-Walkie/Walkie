package com.whyranoid.presentation.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun WalkieTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    text: String = "",
    readOnly: Boolean = false,
    isEnabled: Boolean = true,
    isValidValue: Boolean? = null,
    textStyle: TextStyle = WalkieTypography.Caption.copy(
        fontWeight = FontWeight.Bold
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailings: (@Composable RowScope.() -> Unit) = {},
    onValueChange: (String) -> Unit = {}
) {
    val updatedFocusRequester = rememberUpdatedState(newValue = focusRequester)

    Column(

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE4E4E4),
                    shape = RoundedCornerShape(10.dp)
                )
                .imePadding()
        ) {
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .padding(start = 11.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    value = text,
                    maxLines = 1,
                    textStyle = if (readOnly) {
                        textStyle.copy(Color(0xFFBDBDBD))
                    } else {
                        textStyle
                    },
                    readOnly = readOnly,
                    enabled = isEnabled,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    onValueChange = onValueChange,
                    modifier = modifier
                        .weight(1f)
                        .focusRequester(updatedFocusRequester.value)
                )

                trailings()
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isValidValue == true) {
            Text(
                text = "사용 가능한 닉네임입니다.",
                color = Color(0xFF414EF5),
                style = textStyle
            )
        } else if (isValidValue == false) {
            Text(
                text = "이미 사용 중인 닉네임입니다.",
                color = Color(0xFFFF3257),
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWalkieTextField() {
    WalkieTheme {
        WalkieTextField(
            text = "홍길동",
            focusRequester = FocusRequester(),
            readOnly = true,
            trailings = {
                Text(
                    text = "확인",
                    style = WalkieTypography.Caption.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        ) {

        }
    }
}