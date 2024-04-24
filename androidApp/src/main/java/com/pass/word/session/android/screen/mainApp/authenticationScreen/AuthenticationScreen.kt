package com.pass.word.session.android.screen.mainApp.authenticationScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.navigation.screen.mainApp.authentication.AuthenticationScreenContent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthStateEvent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.itemBoxToCode
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
    val passItemToCodeItem = remember { mutableStateOf("") }



    LaunchedEffect(passItem) {
        when (passItem.length) {
            1 -> {
                passItemToCodeItem.value = "•"
            }

            2 -> {
                passItemToCodeItem.value = "••"
            }

            3 -> {
                passItemToCodeItem.value = "•••"
            }

            4 -> {
                if (passItem[0] == '!') {
                    passItemToCodeItem.value = passItem
                } else {
                    passItemToCodeItem.value = "••••"
                }
            }
        }
    }

    component.passItem.observe {
        Log.d("authScreen", "item $it")
    }

    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                passItemToCodeItem.value = "----"
                snackBarHostState.showSnackbar(it)
                passItemToCodeItem.value = ""
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
            checkUseBiometric(
                context = context,
                onAction = { successState: Boolean, message: String? ->
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
            passItem = passItemToCodeItem.value,
            context = context,
            eventComponentDispatch = {
                component.event(it)
            },
            handlerBtnFinger = {
                checkUseBiometric(context, onAction = { successState, message ->
                    component.event(
                        ScreenAuthStateEvent.ReactionToFollowingBiometrics(
                            successState = successState,
                            errorMessage = message,
                            context = context
                        )
                    )
                })
            }
        )
    }
}

