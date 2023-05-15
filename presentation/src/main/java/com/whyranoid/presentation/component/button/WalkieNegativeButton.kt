package com.whyranoid.presentation.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WalkieNegativeButton(
    text: String,
    onClicked: () -> Unit = {}
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFFB8947),
                shape = RoundedCornerShape(15.dp)
            ),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = Color(0xFFFB8947)
        ),
        onClick = { onClicked() }) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
        )
    }
}