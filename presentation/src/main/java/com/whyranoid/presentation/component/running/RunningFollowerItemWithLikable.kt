package com.whyranoid.presentation.component.running

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.component.community.RunningFollowerItem
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTheme

@Composable
fun RunningFollowerItemWithLikable(
    user: User = User.DUMMY,
    onClick: ((uid: Long) -> Unit)? = null,
    circleBorderColor: Color = WalkieColor.Primary,
    isLiked: Boolean = false,
) {
    // var isLiked by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier.clip(CircleShape).clickable {
            onClick?.invoke(user.uid)
//                .onSuccess {
//                isLiked = true
//            }
        },
    ) {
        RunningFollowerItem(user = user, isDisplayName = false, circleBorderColor)
        if (isLiked) {
            Icon(
                Icons.Default.Favorite,
                tint = WalkieColor.Primary,
                modifier = Modifier.size(20.dp).align(Alignment.BottomCenter),
                contentDescription = "like image",
            )
        }
    }
}

@Composable
@Preview
fun RunningFollowerItemWithLikablePreview() {
    WalkieTheme {
        RunningFollowerItemWithLikable(onClick = { Result.success(Unit) })
    }
}
