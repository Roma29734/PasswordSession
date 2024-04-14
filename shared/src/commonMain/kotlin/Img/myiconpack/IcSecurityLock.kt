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

public val MyIconPack.IcSecurityLock: ImageVector
    get() {
        if (_icSecurityLock != null) {
            return _icSecurityLock!!
        }
        _icSecurityLock = Builder(name = "IcSecurityLock", defaultWidth = 516.38.dp, defaultHeight =
                516.38.dp, viewportWidth = 516.38f, viewportHeight = 516.38f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(401.63f, 210.38f)
                verticalLineToRelative(-66.94f)
                curveTo(401.63f, 65.03f, 336.6f, 0.0f, 258.19f, 0.0f)
                curveTo(179.77f, 0.0f, 114.75f, 65.03f, 114.75f, 143.44f)
                verticalLineToRelative(66.94f)
                curveToRelative(-32.51f, 0.0f, -57.38f, 24.86f, -57.38f, 57.38f)
                verticalLineTo(459.0f)
                curveToRelative(0.0f, 32.51f, 24.86f, 57.38f, 57.38f, 57.38f)
                horizontalLineToRelative(286.88f)
                curveTo(434.14f, 516.38f, 459.0f, 491.51f, 459.0f, 459.0f)
                verticalLineTo(267.75f)
                curveTo(459.0f, 237.15f, 434.14f, 210.38f, 401.63f, 210.38f)
                close()
                moveTo(267.75f, 361.46f)
                verticalLineToRelative(49.72f)
                curveToRelative(0.0f, 5.74f, -3.83f, 9.56f, -9.56f, 9.56f)
                reflectiveCurveToRelative(-9.56f, -3.83f, -9.56f, -9.56f)
                verticalLineToRelative(-49.72f)
                curveToRelative(-11.48f, -3.83f, -19.13f, -15.3f, -19.13f, -26.77f)
                curveToRelative(0.0f, -15.3f, 13.39f, -28.69f, 28.69f, -28.69f)
                reflectiveCurveToRelative(28.69f, 13.39f, 28.69f, 28.69f)
                curveTo(286.88f, 348.08f, 279.23f, 357.64f, 267.75f, 361.46f)
                close()
                moveTo(344.25f, 210.38f)
                horizontalLineTo(172.13f)
                verticalLineToRelative(-66.94f)
                curveToRelative(0.0f, -47.81f, 38.25f, -86.06f, 86.06f, -86.06f)
                reflectiveCurveToRelative(86.06f, 38.25f, 86.06f, 86.06f)
                verticalLineTo(210.38f)
                close()
            }
        }
        .build()
        return _icSecurityLock!!
    }

private var _icSecurityLock: ImageVector? = null
