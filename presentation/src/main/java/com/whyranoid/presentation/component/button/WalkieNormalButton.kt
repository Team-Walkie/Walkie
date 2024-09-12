package com.whyranoid.presentation.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whyranoid.presentation.theme.WalkieTheme

@Composable
fun WalkieNormalButton(
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Button(
        onClick = {
            onButtonClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            backgroundColor = Color(0xFFEEEEEE)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp),
        shape = RoundedCornerShape(7.dp),
        elevation = null
    ) {
        Text(
            text = buttonText,
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp
            )
        )
    }
}

@Preview
@Composable
fun WalkieNormalButtonPreview() {
    WalkieTheme {
        WalkieNormalButton("전체 뱃지 보기") {}
    }
}