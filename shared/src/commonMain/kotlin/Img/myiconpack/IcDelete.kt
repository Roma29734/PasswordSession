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

public val MyIconPack.IcDelete: ImageVector
    get() {
        if (_icDelete != null) {
            return _icDelete!!
        }
        _icDelete = Builder(name = "IcDelete", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(280.0f, 840.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
                reflectiveQuadTo(200.0f, 760.0f)
                verticalLineToRelative(-520.0f)
                horizontalLineToRelative(-40.0f)
                verticalLineToRelative(-80.0f)
                horizontalLineToRelative(200.0f)
                verticalLineToRelative(-40.0f)
                horizontalLineToRelative(240.0f)
                verticalLineToRelative(40.0f)
                horizontalLineToRelative(200.0f)
                verticalLineToRelative(80.0f)
                horizontalLineToRelative(-40.0f)
                verticalLineToRelative(520.0f)
                quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
                reflectiveQuadTo(680.0f, 840.0f)
                lineTo(280.0f, 840.0f)
                close()
                moveTo(680.0f, 240.0f)
                lineTo(280.0f, 240.0f)
                verticalLineToRelative(520.0f)
                horizontalLineToRelative(400.0f)
                verticalLineToRelative(-520.0f)
                close()
                moveTo(360.0f, 680.0f)
                horizontalLineToRelative(80.0f)
                verticalLineToRelative(-360.0f)
                horizontalLineToRelative(-80.0f)
                verticalLineToRelative(360.0f)
                close()
                moveTo(520.0f, 680.0f)
                horizontalLineToRelative(80.0f)
                verticalLineToRelative(-360.0f)
                horizontalLineToRelative(-80.0f)
                verticalLineToRelative(360.0f)
                close()
                moveTo(280.0f, 240.0f)
                verticalLineToRelative(520.0f)
                verticalLineToRelative(-520.0f)
                close()
            }
        }
        .build()
        return _icDelete!!
    }

private var _icDelete: ImageVector? = null
