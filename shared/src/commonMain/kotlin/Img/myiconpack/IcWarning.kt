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

public val MyIconPack.IcWarning: ImageVector
    get() {
        if (_icWarning != null) {
            return _icWarning!!
        }
        _icWarning = Builder(name = "IcWarning", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(480.0f, 640.0f)
                quadToRelative(17.0f, 0.0f, 28.5f, -11.5f)
                reflectiveQuadTo(520.0f, 600.0f)
                quadToRelative(0.0f, -17.0f, -11.5f, -28.5f)
                reflectiveQuadTo(480.0f, 560.0f)
                quadToRelative(-17.0f, 0.0f, -28.5f, 11.5f)
                reflectiveQuadTo(440.0f, 600.0f)
                quadToRelative(0.0f, 17.0f, 11.5f, 28.5f)
                reflectiveQuadTo(480.0f, 640.0f)
                close()
                moveTo(440.0f, 480.0f)
                horizontalLineToRelative(80.0f)
                verticalLineToRelative(-200.0f)
                horizontalLineToRelative(-80.0f)
                verticalLineToRelative(200.0f)
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
        return _icWarning!!
    }

private var _icWarning: ImageVector? = null
