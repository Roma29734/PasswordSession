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

public val MyIconPack.IcSave: ImageVector
    get() {
        if (_icSave != null) {
            return _icSave!!
        }
        _icSave = Builder(name = "IcSave", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(480.0f, 640.0f)
                lineTo(280.0f, 440.0f)
                lineToRelative(56.0f, -58.0f)
                lineToRelative(104.0f, 104.0f)
                verticalLineToRelative(-326.0f)
                horizontalLineToRelative(80.0f)
                verticalLineToRelative(326.0f)
                lineToRelative(104.0f, -104.0f)
                lineToRelative(56.0f, 58.0f)
                lineToRelative(-200.0f, 200.0f)
                close()
                moveTo(240.0f, 800.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
                reflectiveQuadTo(160.0f, 720.0f)
                verticalLineToRelative(-120.0f)
                horizontalLineToRelative(80.0f)
                verticalLineToRelative(120.0f)
                horizontalLineToRelative(480.0f)
                verticalLineToRelative(-120.0f)
                horizontalLineToRelative(80.0f)
                verticalLineToRelative(120.0f)
                quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
                reflectiveQuadTo(720.0f, 800.0f)
                lineTo(240.0f, 800.0f)
                close()
            }
        }
        .build()
        return _icSave!!
    }

private var _icSave: ImageVector? = null
