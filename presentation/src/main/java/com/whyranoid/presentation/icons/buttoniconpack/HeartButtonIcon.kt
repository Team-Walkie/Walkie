package com.whyranoid.presentation.icons.buttoniconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val HeartButtonIcon: ImageVector
    get() {
        if (_heartButtonIcon != null) {
            return _heartButtonIcon!!
        }
        _heartButtonIcon = Builder(name = "HeartButtonIcon", defaultWidth = 20.0.dp, defaultHeight =
                19.0.dp, viewportWidth = 20.0f, viewportHeight = 19.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.2232f, 16.2905f)
                lineTo(9.2217f, 16.2892f)
                curveTo(6.6271f, 13.9364f, 4.5541f, 12.0515f, 3.118f, 10.2946f)
                curveTo(1.693f, 8.5512f, 1.0f, 7.0562f, 1.0f, 5.5f)
                curveTo(1.0f, 2.9635f, 2.9711f, 1.0f, 5.5f, 1.0f)
                curveTo(6.9377f, 1.0f, 8.3341f, 1.6745f, 9.2412f, 2.7313f)
                lineTo(10.0f, 3.6154f)
                lineTo(10.7588f, 2.7313f)
                curveTo(11.6659f, 1.6745f, 13.0623f, 1.0f, 14.5f, 1.0f)
                curveTo(17.0289f, 1.0f, 19.0f, 2.9635f, 19.0f, 5.5f)
                curveTo(19.0f, 7.0562f, 18.307f, 8.5512f, 16.882f, 10.2946f)
                curveTo(15.4459f, 12.0515f, 13.3729f, 13.9364f, 10.7783f, 16.2892f)
                lineTo(10.7768f, 16.2905f)
                lineTo(10.0f, 16.9977f)
                lineTo(9.2232f, 16.2905f)
                close()
            }
        }
        .build()
        return _heartButtonIcon!!
    }

private var _heartButtonIcon: ImageVector? = null
