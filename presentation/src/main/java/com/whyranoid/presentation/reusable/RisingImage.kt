package com.whyranoid.presentation.reusable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.util.random
import kotlinx.coroutines.launch

private val RISING_ANIMATION_TIME_RANGE = (3000..5000)
private val ANIMATION_MOVE_RANGE = (500..1500)

@Composable
fun RisingImage(
    modifier: Modifier = Modifier,
    painter: Painter,
) {

    // TODO: CompositionLocal
    val configuration = LocalConfiguration.current

    val screenHeight: Float
    val screenWidth: Float

    with(LocalDensity.current) {
        screenWidth = configuration.screenWidthDp.dp.toPx()
        screenHeight = configuration.screenHeightDp.dp.toPx()
    }

    var initOffSetY = 0f
    val offsetX = remember { Animatable((0f..screenWidth).random()) }
    val offsetY = remember {
        Animatable((0f..screenHeight).random()
            .apply { initOffSetY = this }
        )
    }
    val opacity = remember { Animatable(1f) }

    Image(
        painter = painter,
        contentDescription = "risingAnimationImage", contentScale = ContentScale.Crop,
        modifier = modifier
            .offset {
                IntOffset(
                    offsetX.value.toInt(),
                    offsetY.value.toInt()
                )
            },
        alpha = opacity.value
    )

    LaunchedEffect(true) {
        launch {
            offsetY.animateTo(
                targetValue = initOffSetY - ANIMATION_MOVE_RANGE.random(),
                animationSpec = tween(RISING_ANIMATION_TIME_RANGE.random())
            )
        }
        launch {
            opacity.animateTo(
                targetValue = 0f,
                animationSpec = tween(RISING_ANIMATION_TIME_RANGE.random())
            )
        }
    }
}