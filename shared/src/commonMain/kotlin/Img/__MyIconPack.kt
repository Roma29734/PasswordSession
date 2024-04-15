package Img

import Img.myiconpack.IcArrowBackNav
import Img.myiconpack.IcComplete
import Img.myiconpack.IcCopySourse
import Img.myiconpack.IcDelete
import Img.myiconpack.IcDownMore
import Img.myiconpack.IcEdit
import Img.myiconpack.IcError
import Img.myiconpack.IcFingerPrint
import Img.myiconpack.IcImport
import Img.myiconpack.IcKeyVariantTow
import Img.myiconpack.IcLauncherForeground
import Img.myiconpack.IcLogoGitHub
import Img.myiconpack.IcLogoTelegram
import Img.myiconpack.IcPassword
import Img.myiconpack.IcPasswordVertical
import Img.myiconpack.IcPin
import Img.myiconpack.IcSave
import Img.myiconpack.IcSecurityLock
import Img.myiconpack.IcWarning
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.List as ____KtList

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(IcArrowBackNav, IcComplete, IcCopySourse, IcDelete, IcDownMore, IcEdit,
        IcError, IcFingerPrint, IcImport, IcKeyVariantTow, IcLauncherForeground, IcLogoGitHub,
        IcLogoTelegram, IcPassword, IcPasswordVertical, IcPin, IcSave, IcSecurityLock, IcWarning)
    return __AllIcons!!
  }
