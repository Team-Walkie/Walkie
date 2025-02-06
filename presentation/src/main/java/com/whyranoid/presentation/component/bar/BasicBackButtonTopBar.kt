package com.whyranoid.presentation.component.bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.R

@Composable
fun BasicBackButtonTopBar(
    onClicked: () -> Unit
) {
    Row {
        IconButton(
            modifier = Modifier
                .padding(vertical = 19.dp)
                .padding(start = 16.dp),
            onClick = { onClicked() }) {
            Icon(
                painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "back arrow"
            )
        }
    }
}