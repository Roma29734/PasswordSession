package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.settingsScreen

import Img.MyIconPack
import Img.myiconpack.IcWarning
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ItemSettings
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsStateEvent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.SettingsScreenContent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomImageModel
import com.pass.word.session.ui.viewComponent.DialogLogOut
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(component: ScreenSettingsComponent) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val itemSettingsList by component.itemSettingsList.collectAsState()
    val stateVisibleDialog by component.stateVisibleDialog.collectAsState()

    if (stateVisibleDialog is StateBasicDialog.Show) {
        Dialog(onDismissRequest = { stateVisibleDialog is StateBasicDialog.Hide }) {
            DialogLogOut(
                cancelHandler = {
                    component.onEvent(ScreenSettingsStateEvent.OnClickInDialogButton(false, DriverFactory(context = context)))
                },
                continueHandler = {
                    component.onEvent(ScreenSettingsStateEvent.OnClickInDialogButton(true, DriverFactory(context = context)))
                },
                textCancelButton = "Cancel",
                textContinueButton = "Continue",
                customImageModel = CustomImageModel(
                    painter = MyIconPack.IcWarning,
                    color = CustomColor().brandRedMain,
                    contentScale = ContentScale.Fit
                ),
                notifiText = "Dangerous",
                subTitleText = "when you click continue, you exit the application and all data will be erased forever, they will remain only in the blockchain",
                startTime = 15
            )
        }
    }
    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
        onDispose {
            cancel()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {

        SettingsScreenContent(
            itemSettingsList = itemSettingsList,
            eventComponentDispatch = {
                component.onEvent(it)
            },
            openUrlHandler = {
                openCustomTab(it, context)
            },
            mainComponentBtnHandler = {
                component.onEvent(ScreenSettingsStateEvent.ClickToButtonDownloadPass("adad", DriverFactory(context = context)))
            }
        )
    }
}

fun openCustomTab(url: String, context: Context) {
    val packageName = "com.android.chrome"

    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)

    val customBuilder = builder.build()

    // on below line we are checking if the package name is null or not.
    customBuilder.intent.setPackage(packageName)

    customBuilder.launchUrl(context, Uri.parse(url))
}