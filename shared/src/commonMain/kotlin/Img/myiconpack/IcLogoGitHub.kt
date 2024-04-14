package Img.myiconpack

import Img.MyIconPack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val MyIconPack.IcLogoGitHub: ImageVector
    get() {
        if (_icLogoGitHub != null) {
            return _icLogoGitHub!!
        }
        _icLogoGitHub = Builder(name = "IcLogoGitHub", defaultWidth = 48.0.dp, defaultHeight =
                47.0.dp, viewportWidth = 48.0f, viewportHeight = 47.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(24.0f, 0.0f)
                curveTo(37.255f, 0.0f, 48.0f, 10.682f, 48.0f, 23.861f)
                curveTo(48.0f, 34.402f, 41.131f, 43.343f, 31.601f, 46.501f)
                curveTo(30.384f, 46.736f, 29.952f, 45.991f, 29.952f, 45.356f)
                curveTo(29.952f, 44.569f, 29.981f, 42.0f, 29.981f, 38.807f)
                curveTo(29.981f, 36.582f, 29.213f, 35.13f, 28.351f, 34.39f)
                curveTo(33.696f, 33.799f, 39.312f, 31.781f, 39.312f, 22.616f)
                curveTo(39.312f, 20.009f, 38.381f, 17.882f, 36.84f, 16.211f)
                curveTo(37.09f, 15.609f, 37.913f, 13.182f, 36.605f, 9.895f)
                curveTo(36.605f, 9.895f, 34.594f, 9.256f, 30.012f, 12.342f)
                curveTo(28.094f, 11.814f, 26.04f, 11.548f, 24.0f, 11.539f)
                curveTo(21.96f, 11.548f, 19.908f, 11.814f, 17.993f, 12.342f)
                curveTo(13.406f, 9.256f, 11.39f, 9.895f, 11.39f, 9.895f)
                curveTo(10.087f, 13.182f, 10.91f, 15.609f, 11.158f, 16.211f)
                curveTo(9.624f, 17.882f, 8.686f, 20.009f, 8.686f, 22.616f)
                curveTo(8.686f, 31.757f, 14.29f, 33.806f, 19.62f, 34.409f)
                curveTo(18.934f, 35.005f, 18.312f, 36.056f, 18.096f, 37.599f)
                curveTo(16.728f, 38.209f, 13.253f, 39.264f, 11.112f, 35.617f)
                curveTo(11.112f, 35.617f, 9.842f, 33.324f, 7.433f, 33.157f)
                curveTo(7.433f, 33.157f, 5.093f, 33.127f, 7.27f, 34.607f)
                curveTo(7.27f, 34.607f, 8.842f, 35.34f, 9.934f, 38.098f)
                curveTo(9.934f, 38.098f, 11.342f, 42.356f, 18.019f, 40.914f)
                curveTo(18.031f, 42.908f, 18.053f, 44.788f, 18.053f, 45.356f)
                curveTo(18.053f, 45.986f, 17.611f, 46.724f, 16.414f, 46.503f)
                curveTo(6.876f, 43.35f, 0.0f, 34.404f, 0.0f, 23.861f)
                curveTo(0.0f, 10.682f, 10.747f, 0.0f, 24.0f, 0.0f)
                close()
            }
        }
        .build()
        return _icLogoGitHub!!
    }

private var _icLogoGitHub: ImageVector? = null
