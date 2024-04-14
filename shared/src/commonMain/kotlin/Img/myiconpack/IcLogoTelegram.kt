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

public val MyIconPack.IcLogoTelegram: ImageVector
    get() {
        if (_icLogoTelegram != null) {
            return _icLogoTelegram!!
        }
        _icLogoTelegram = Builder(name = "IcLogoTelegram", defaultWidth = 48.0.dp, defaultHeight =
                48.0.dp, viewportWidth = 48.0f, viewportHeight = 48.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(33.812f, 14.366f)
                curveTo(33.821f, 14.366f, 33.834f, 14.366f, 33.847f, 14.366f)
                curveTo(34.18f, 14.366f, 34.489f, 14.47f, 34.743f, 14.649f)
                lineTo(34.738f, 14.645f)
                curveTo(34.923f, 14.806f, 35.047f, 15.033f, 35.077f, 15.288f)
                verticalLineTo(15.292f)
                curveTo(35.109f, 15.487f, 35.127f, 15.71f, 35.127f, 15.938f)
                curveTo(35.127f, 16.041f, 35.124f, 16.143f, 35.116f, 16.245f)
                verticalLineTo(16.231f)
                curveTo(34.757f, 20.006f, 33.202f, 29.162f, 32.411f, 33.388f)
                curveTo(32.076f, 35.178f, 31.418f, 35.777f, 30.781f, 35.834f)
                curveTo(29.396f, 35.963f, 28.344f, 34.919f, 27.002f, 34.04f)
                curveTo(24.902f, 32.661f, 23.715f, 31.804f, 21.677f, 30.461f)
                curveTo(19.319f, 28.91f, 20.848f, 28.054f, 22.19f, 26.661f)
                curveTo(22.542f, 26.295f, 28.647f, 20.741f, 28.767f, 20.237f)
                curveTo(28.773f, 20.207f, 28.776f, 20.174f, 28.776f, 20.139f)
                curveTo(28.776f, 20.014f, 28.73f, 19.901f, 28.655f, 19.815f)
                curveTo(28.572f, 19.761f, 28.469f, 19.731f, 28.36f, 19.731f)
                curveTo(28.288f, 19.731f, 28.22f, 19.745f, 28.156f, 19.769f)
                lineTo(28.159f, 19.767f)
                curveTo(27.949f, 19.815f, 24.594f, 22.032f, 18.094f, 26.419f)
                curveTo(17.385f, 26.979f, 16.489f, 27.332f, 15.514f, 27.374f)
                horizontalLineTo(15.504f)
                curveTo(14.123f, 27.206f, 12.868f, 26.899f, 11.678f, 26.461f)
                lineTo(11.796f, 26.499f)
                curveTo(10.301f, 26.011f, 9.114f, 25.755f, 9.216f, 24.929f)
                curveTo(9.27f, 24.5f, 9.862f, 24.061f, 10.991f, 23.611f)
                curveTo(17.948f, 20.58f, 22.587f, 18.582f, 24.908f, 17.618f)
                curveTo(27.469f, 16.258f, 30.438f, 15.139f, 33.56f, 14.414f)
                lineTo(33.81f, 14.365f)
                lineTo(33.812f, 14.366f)
                close()
                moveTo(23.944f, 0.0f)
                curveTo(10.714f, 0.032f, 0.0f, 10.765f, 0.0f, 24.0f)
                curveTo(0.0f, 37.254f, 10.744f, 48.0f, 24.0f, 48.0f)
                curveTo(37.256f, 48.0f, 48.0f, 37.256f, 48.0f, 24.0f)
                curveTo(48.0f, 10.765f, 37.286f, 0.032f, 24.059f, 0.0f)
                horizontalLineTo(24.056f)
                curveTo(24.019f, 0.0f, 23.981f, 0.0f, 23.944f, 0.0f)
                close()
            }
        }
        .build()
        return _icLogoTelegram!!
    }

private var _icLogoTelegram: ImageVector? = null
