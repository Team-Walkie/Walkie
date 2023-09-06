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

val CommentButtonIcon: ImageVector
    get() {
        if (_commentButtonIcon != null) {
            return _commentButtonIcon!!
        }
        _commentButtonIcon = Builder(name = "CommentButtonIcon", defaultWidth = 20.0.dp,
                defaultHeight = 19.0.dp, viewportWidth = 20.0f, viewportHeight = 19.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(13.2365f, 15.4052f)
                lineTo(13.7792f, 15.2385f)
                lineTo(14.2005f, 15.6192f)
                curveTo(15.428f, 16.7285f, 16.7818f, 17.0892f, 17.8621f, 17.168f)
                curveTo(17.6877f, 16.9747f, 17.5257f, 16.7887f, 17.3804f, 16.6101f)
                curveTo(16.8621f, 15.9727f, 16.4395f, 15.2964f, 16.2694f, 14.4461f)
                lineTo(16.1569f, 13.8833f)
                lineTo(16.586f, 13.5022f)
                curveTo(18.1042f, 12.1542f, 19.0f, 10.3669f, 19.0f, 8.4444f)
                curveTo(19.0f, 4.4844f, 15.1146f, 1.0f, 10.0f, 1.0f)
                curveTo(4.8854f, 1.0f, 1.0f, 4.4844f, 1.0f, 8.4444f)
                curveTo(1.0f, 12.4045f, 4.8854f, 15.8889f, 10.0f, 15.8889f)
                curveTo(11.1379f, 15.8889f, 12.229f, 15.7145f, 13.2365f, 15.4052f)
                close()
            }
        }
        .build()
        return _commentButtonIcon!!
    }

private var _commentButtonIcon: ImageVector? = null
