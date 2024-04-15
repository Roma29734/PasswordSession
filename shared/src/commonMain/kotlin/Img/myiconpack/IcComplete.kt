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

public val MyIconPack.IcComplete: ImageVector
    get() {
        if (_icComplete != null) {
            return _icComplete!!
        }
        _icComplete = Builder(name = "IcComplete", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(438.0f, 622.0f)
                lineToRelative(226.0f, -226.0f)
                lineToRelative(-57.0f, -57.0f)
                lineToRelative(-169.0f, 169.0f)
                lineToRelative(-84.0f, -84.0f)
                lineToRelative(-57.0f, 57.0f)
                lineToRelative(141.0f, 141.0f)
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
        return _icComplete!!
    }

private var _icComplete: ImageVector? = null
