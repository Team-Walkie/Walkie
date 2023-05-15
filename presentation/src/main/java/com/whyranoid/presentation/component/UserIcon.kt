package com.whyranoid.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.util.bouncingClickable

@Composable
fun UserIcon(
    user: User,
    onClicked: () -> Unit = {}
) {
    AsyncImage(model = user.imageUrl, contentDescription = "",
    modifier = Modifier
        .bouncingClickable {
            onClicked()
        }
        .size(58.dp)
        .clip(CircleShape),
    contentScale = ContentScale.Crop)
}