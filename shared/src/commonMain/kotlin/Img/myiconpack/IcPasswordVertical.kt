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

public val MyIconPack.IcPasswordVertical: ImageVector
    get() {
        if (_icPasswordVertical != null) {
            return _icPasswordVertical!!
        }
        _icPasswordVertical = Builder(name = "IcPasswordVertical", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(420.0f, 280.0f)
                quadToRelative(0.0f, -33.0f, 23.5f, -56.5f)
                reflectiveQuadTo(500.0f, 200.0f)
                quadToRelative(33.0f, 0.0f, 56.5f, 23.5f)
                reflectiveQuadTo(580.0f, 280.0f)
                quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
                reflectiveQuadTo(500.0f, 360.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
                reflectiveQuadTo(420.0f, 280.0f)
                close()
                moveTo(500.0f, 960.0f)
                lineTo(320.0f, 780.0f)
                lineToRelative(60.0f, -80.0f)
                lineToRelative(-60.0f, -80.0f)
                lineToRelative(60.0f, -85.0f)
                verticalLineToRelative(-47.0f)
                quadToRelative(-54.0f, -32.0f, -87.0f, -86.5f)
                reflectiveQuadTo(260.0f, 280.0f)
                quadToRelative(0.0f, -100.0f, 70.0f, -170.0f)
                reflectiveQuadToRelative(170.0f, -70.0f)
                quadToRelative(100.0f, 0.0f, 170.0f, 70.0f)
                reflectiveQuadToRelative(70.0f, 170.0f)
                quadToRelative(0.0f, 67.0f, -33.0f, 121.5f)
                reflectiveQuadTo(620.0f, 488.0f)
                verticalLineToRelative(352.0f)
                lineTo(500.0f, 960.0f)
                close()
                moveTo(340.0f, 280.0f)
                quadToRelative(0.0f, 56.0f, 34.0f, 98.5f)
                reflectiveQuadToRelative(86.0f, 56.5f)
                verticalLineToRelative(125.0f)
                lineToRelative(-41.0f, 58.0f)
                lineToRelative(61.0f, 82.0f)
                lineToRelative(-55.0f, 71.0f)
                lineToRelative(75.0f, 75.0f)
                lineToRelative(40.0f, -40.0f)
                verticalLineToRelative(-371.0f)
                quadToRelative(52.0f, -14.0f, 86.0f, -56.5f)
                reflectiveQuadToRelative(34.0f, -98.5f)
                quadToRelative(0.0f, -66.0f, -47.0f, -113.0f)
                reflectiveQuadToRelative(-113.0f, -47.0f)
                quadToRelative(-66.0f, 0.0f, -113.0f, 47.0f)
                reflectiveQuadToRelative(-47.0f, 113.0f)
                close()
            }
        }
        .build()
        return _icPasswordVertical!!
    }

private var _icPasswordVertical: ImageVector? = null
