package Img.myiconpack

import Img.MyIconPack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val MyIconPack.IcError: ImageVector
    get() {
        if (_icError != null) {
            return _icError!!
        }
        _icError = Builder(name = "IcError", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(396.0f, 620.0f)
                lineToRelative(84.0f, -84.0f)
                lineToRelative(84.0f, 84.0f)
                lineToRelative(56.0f, -56.0f)
                lineToRelative(-84.0f, -84.0f)
                lineToRelative(84.0f, -84.0f)
                lineToRelative(-56.0f, -56.0f)
                lineToRelative(-84.0f, 84.0f)
                lineToRelative(-84.0f, -84.0f)
                lineToRelative(-56.0f, 56.0f)
                lineToRelative(84.0f, 84.0f)
                lineToRelative(-84.0f, 84.0f)
                lineToRelative(56.0f, 56.0f)
                close()
                moveTo(480.0f, 880.0f)
                quadToRelative(-139.0f, -35.0f, -229.5f, -159.5f)
                reflectiveQuadTo(160.0f, 444.0f)
                verticalLineToRelative(-244.0f)
                lineToRelative(320.0f, -120.0f)
                lineToRelative(320.0f, 120.0f)
                verticalLineToRelative(244.0f)
                quadToRelative(0.0f, 152.0f, -90.5f, 276.5f)
                reflectiveQuadTo(480.0f, 880.0f)
                close()
                moveTo(480.0f, 796.0f)
                quadToRelative(104.0f, -33.0f, 172.0f, -132.0f)
                reflectiveQuadToRelative(68.0f, -220.0f)
                verticalLineToRelative(-189.0f)
                lineToRelative(-240.0f, -90.0f)
                lineToRelative(-240.0f, 90.0f)
                verticalLineToRelative(189.0f)
                quadToRelative(0.0f, 121.0f, 68.0f, 220.0f)
                reflectiveQuadToRelative(172.0f, 132.0f)
                close()
                moveTo(480.0f, 480.0f)
                close()
            }
        }
        .build()
        return _icError!!
    }

private var _icError: ImageVector? = null
