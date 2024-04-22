package com.pass.word.session.android.screen.mainApp.authenticationScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.navigation.screen.mainApp.authentication.AuthenticationScreenContent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthStateEvent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.utilits.checkUseBiometric
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AuthenticationScreen(component: ScreenAuthenticationComponent) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val passItem: String by component.passItem.subscribeAsState()

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            Log.d("tagLogger", "Вызывается sideEffect")
        }
    }

    component.passItem.observe {
        Log.d("authScreen", "item $it")
    }

    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                snackBarHostState.showSnackbar(it)
                println("setUp state clic to false")
                component.clickState = false
            }
        }

        onDispose {
            // Отписка при уничтожении экрана
            cancel()
        }
    }

    DisposableEffect(Unit) {

        try {
            checkUseBiometric(context = context, onAction = { successState: Boolean, message: String? ->
                component.event(
                    ScreenAuthStateEvent.ReactionToFirstBiometrics(
                        successState = successState,
                        errorMessage = message,
                        context = context
                    )
                )
            })
        } catch (e: Exception) {

        }

        onDispose {}
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        AuthenticationScreenContent(
            passItem = passItem,
            context = null,
            eventComponentDispatch = {
                component.event(it)
            }
        )
    }
}