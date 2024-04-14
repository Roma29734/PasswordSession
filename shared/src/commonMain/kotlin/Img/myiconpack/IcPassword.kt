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

public val MyIconPack.IcPassword: ImageVector
    get() {
        if (_icPassword != null) {
            return _icPassword!!
        }
        _icPassword = Builder(name = "IcPassword", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(280.0f, 560.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
                reflectiveQuadTo(200.0f, 480.0f)
                quadToRelative(0.0f, -33.0f, 23.5f, -56.5f)
                reflectiveQuadTo(280.0f, 400.0f)
                quadToRelative(33.0f, 0.0f, 56.5f, 23.5f)
                reflectiveQuadTo(360.0f, 480.0f)
                quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
                reflectiveQuadTo(280.0f, 560.0f)
                close()
                moveTo(280.0f, 720.0f)
                quadToRelative(-100.0f, 0.0f, -170.0f, -70.0f)
                reflectiveQuadTo(40.0f, 480.0f)
                quadToRelative(0.0f, -100.0f, 70.0f, -170.0f)
                reflectiveQuadToRelative(170.0f, -70.0f)
                quadToRelative(67.0f, 0.0f, 121.5f, 33.0f)
                reflectiveQuadToRelative(86.5f, 87.0f)
                horizontalLineToRelative(352.0f)
                lineToRelative(120.0f, 120.0f)
                lineToRelative(-180.0f, 180.0f)
                lineToRelative(-80.0f, -60.0f)
                lineToRelative(-80.0f, 60.0f)
                lineToRelative(-85.0f, -60.0f)
                horizontalLineToRelative(-47.0f)
                quadToRelative(-32.0f, 54.0f, -86.5f, 87.0f)
                reflectiveQuadTo(280.0f, 720.0f)
                close()
                moveTo(280.0f, 640.0f)
                quadToRelative(56.0f, 0.0f, 98.5f, -34.0f)
                reflectiveQuadToRelative(56.5f, -86.0f)
                horizontalLineToRelative(125.0f)
                lineToRelative(58.0f, 41.0f)
                lineToRelative(82.0f, -61.0f)
                lineToRelative(71.0f, 55.0f)
                lineToRelative(75.0f, -75.0f)
                lineToRelative(-40.0f, -40.0f)
                lineTo(435.0f, 440.0f)
                quadToRelative(-14.0f, -52.0f, -56.5f, -86.0f)
                reflectiveQuadTo(280.0f, 320.0f)
                quadToRelative(-66.0f, 0.0f, -113.0f, 47.0f)
                reflectiveQuadToRelative(-47.0f, 113.0f)
                quadToRelative(0.0f, 66.0f, 47.0f, 113.0f)
                reflectiveQuadToRelative(113.0f, 47.0f)
                close()
            }
        }
        .build()
        return _icPassword!!
    }

private var _icPassword: ImageVector? = null
