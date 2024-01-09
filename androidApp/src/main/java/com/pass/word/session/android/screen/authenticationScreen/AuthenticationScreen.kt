package com.pass.word.session.android.screen.authenticationScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.navigation.screen.main.authentication.ScreenAuthStateEvent
import com.pass.word.session.navigation.screen.main.authentication.ScreenAuthenticationComponent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AuthenticationScreen(component: ScreenAuthenticationComponent) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val passItem: String by component.passItem.subscribeAsState()

    component.passItem.observe {
        Log.d("authScreen", "item $it")
    }

    DisposableEffect(component) {
        val listenerSnachBarShow: (String) -> Unit = {
            scope.launch {
                snackBarHostState.showSnackbar("$it")
            }
        }
        component.subscribeListenerSnackBar(listenerSnachBarShow)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerSnackBar(listenerSnachBarShow)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Enter your authentication code ${passItem}",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BoxItemCode(if(passItem.isNotEmpty()) "*" else "")
                BoxItemCode(if(passItem.length >= 2 ) "*" else "")
                BoxItemCode(if(passItem.length >= 3 ) "*" else "")
                BoxItemCode(if(passItem.length >= 4 ) "*" else "")
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonNumber(1) {
                    component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
                ButtonNumber(2) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
                ButtonNumber(3) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonNumber(4) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
                ButtonNumber(5) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
                ButtonNumber(6) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonNumber(7) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
                ButtonNumber(8) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
                ButtonNumber(9) { component.event(ScreenAuthStateEvent.StateUpdatePassItem(it.toString())) }
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box(
                    Modifier
                        .size(64.dp),
                )
                ButtonNumber(textButton = 0) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString()
                        )
                    )
                }
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 16.dp)
                        .clickable {
                            checkBiometrick(context, onAction = { successState, message ->
                                component.event(
                                    ScreenAuthStateEvent.StateBiometric(
                                        successState = successState,
                                        errorMessage = message
                                    )
                                )
                            })
                        },
                    painter = painterResource(id = R.drawable.ic_finger_print),
                    contentDescription = "fingerPrintIco",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}


@Composable
fun BoxItemCode(itemText: String) {
    Box(
        Modifier
            .size(48.dp)
            .border(2.dp,color = CustomColor().grayLight, RoundedCornerShape(600.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = itemText,
            color = Color.White, style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ButtonNumber(textButton: Int, clickHandler: (Int) -> Unit) {
    Box(
        Modifier
            .size(64.dp)
            .background(CustomColor().brandBlueLight, RoundedCornerShape(600.dp))
            .clickable { clickHandler(textButton) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun checkBiometrick(
    context: Context,
    onAction: (successState: Boolean, message: String?) -> Unit,
) {
    var cancellationSignal: CancellationSignal? = null

    fun notifyUser(message: String) {
        Log.d("BIOMETRIC", message)
    }

    fun getCancelletionSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Ath Cancelled via Signal")
        }

        return cancellationSignal as CancellationSignal
    }


    val authenticationCalBack: BiometricPrompt.AuthenticationCallback =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                onAction(false, "Authentication Error $errorCode")
                notifyUser("Authentication Error $errorCode")
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpCode, helpString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                notifyUser("Authentication Succeeded")
                onAction(true, null)
                super.onAuthenticationSucceeded(result)
            }
        }

    fun checkBiometricSupport(context: Context): Boolean {
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isDeviceSecure) {
            onAction(false, "Lock screen security not enabled in the settings")
            notifyUser("Lock screen security not enabled in the settings")
            return false
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("Fingerprint authentication permission not enabled")
            return false
        }

        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun launchBiometric() {
        if (checkBiometricSupport(context)) {
            val biometricPrompt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                BiometricPrompt
                    .Builder(context)
                    .setTitle("Allow Biometric Authentication")
                    .setSubtitle("You will no longer required username and password during login")
                    .setDescription("We use biometric authentication to protect your data")
                    .setNegativeButton("Not Now", context.mainExecutor) { dialogInterface, i ->
                        notifyUser("Authentication cancelled")

                    }
                    .build()
            } else {
                TODO("VERSION.SDK_INT < P")
            }

            biometricPrompt.authenticate(
                getCancelletionSignal(),
                context.mainExecutor,
                authenticationCalBack
            )

        }
    }
    launchBiometric()
}