package com.pass.word.session.android.screen.mainApp.detailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.CustomErrorDialog
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.detail.AlertDialogDelete
import com.pass.word.session.navigation.screen.mainApp.detail.BaseTextItem
import com.pass.word.session.navigation.screen.mainApp.detail.Descriptions
import com.pass.word.session.navigation.screen.mainApp.detail.DetailScreenContent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailComponent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(component: ScreenDetailComponent) {

    val context = LocalContext.current


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val stateOpenAlertDialog: Boolean by component.stateOpenAlertDialog.subscribeAsState()
    val stateLoading by component.stateOpenDialogChoseType.collectAsState()

    val itemModel: PasswordItemModel by component.passwordItem.subscribeAsState()

    component.getOneItem(DriverFactory(context))

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        DetailScreenContent(
            clipboardManager = clipboardManager,
            stateOpenAlertDialog = stateOpenAlertDialog,
            stateLoading = stateLoading,
            itemModel = itemModel,
            driverFactory = DriverFactory(context),
            snackBarHostHandler = {
                scope.launch {
                    snackbarHostState.showSnackbar(it)
                }
            },
            eventComponentDispatch = {
                component.onEvent(it)
            }
        )
    }
}