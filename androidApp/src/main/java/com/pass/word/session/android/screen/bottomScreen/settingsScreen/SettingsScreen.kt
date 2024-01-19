package com.pass.word.session.android.screen.bottomScreen.settingsScreen

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.bottom.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.navigation.screen.bottom.screenSettingsComponent.ScreenSettingsStateEvent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(component: ScreenSettingsComponent) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    DisposableEffect(component) {
        val listenerPassCreated: (message: String) -> Unit = { msg ->
            scope.launch {
                snackBarHostState.showSnackbar(msg)
            }
        }
        component.subscribeListenerToastPush(listenerPassCreated)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerToastPush(listenerPassCreated)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp),
                    text = "Settings",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.size(16.dp))


                ItemSettingsMenu(
                    image = painterResource(id = R.drawable.ic_import),
                    text = "import password",
                    clickHandler = {
                        component.onEvent(ScreenSettingsStateEvent.OnNavigateToImportPassword)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                ItemSettingsMenu(
                    image = painterResource(id = R.drawable.ic_security_lock),
                    text = "change password",
                    clickHandler = {
                        component.onEvent(ScreenSettingsStateEvent.OnNavigateToChangePasswordComponent)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                ItemSettingsMenu(
                    image = painterResource(id = R.drawable.ic_logo_telegram),
                    text = "telegram",
                    clickHandler = {
                        openCustomTab("https://t.me/apkPublicPrograms", context)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                ItemSettingsMenu(
                    image = painterResource(id = R.drawable.ic_logo_git_hub),
                    text = "gitHub",
                    clickHandler = {
                        openCustomTab("https://github.com/Roma29734/PasswordSession", context)
                    }
                )
            }

            MainComponentButton(
                text = "download password",
                clickHandler = {
                    component.onEvent(
                        ScreenSettingsStateEvent.ClickToButtonDownloadPass(
                            context,
                            DriverFactory(context)
                        )
                    )
                }
            )
        }

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

@Composable
fun ItemSettingsMenu(image: Painter, text: String, clickHandler: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { clickHandler() }
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = image,
            contentDescription = "image",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = text, style = MaterialTheme.typography.displayMedium, color = Color.White)
    }
}
