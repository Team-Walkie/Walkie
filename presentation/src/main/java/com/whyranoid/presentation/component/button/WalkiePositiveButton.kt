package com.whyranoid.presentation.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WalkiePositiveButton(
    modifier: Modifier = Modifier,
    text: String,
    onClicked: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape = RoundedCornerShape(15.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFB8947),
            contentColor = Color.White
        ),
        onClick = { onClicked() }) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
        )
    }
}