package com.pass.word.session.android.screen.mainApp.editScreen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.edit.EditScreenContent
import com.pass.word.session.navigation.screen.mainApp.edit.ScreenEditComponent
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditScreen(component: ScreenEditComponent) {

    val context = LocalContext.current
    val textTitle: String by component.textTitle.subscribeAsState()
    val textEmailORUserName: String by component.textEmailOrUserName.subscribeAsState()
    val textPassword: String by component.textPassword.subscribeAsState()
    val textUrl: String by component.textUrl.subscribeAsState()
    val textDescriptions: String by component.textDescriptions.subscribeAsState()

    val stateOpenDialogLoading by component.stateOpenDialogChoseType.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }

        onDispose {
            // Отписка при уничтожении экрана
            cancel()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        EditScreenContent(
            textTitle = textTitle,
            textEmailORUserName = textEmailORUserName,
            textPassword = textPassword,
            textUrl = textUrl,
            textDescriptions = textDescriptions,
            stateOpenDialogLoading = stateOpenDialogLoading,
            driverFactory = DriverFactory(context),
            eventComponentDispatch = { component.onEvent(it) }
        )

    }
}