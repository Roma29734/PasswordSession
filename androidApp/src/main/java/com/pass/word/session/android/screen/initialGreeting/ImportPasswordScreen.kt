package com.pass.word.session.android.screen.initialGreeting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.main.detail.ScreenDetailEvent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenImportPassword.ScreenImportPasswordEvent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImportPasswordScreen(
    component: ScreenImportPasswordComponent,
) {

    val context = LocalContext.current

    val stateShowCompleteView: Boolean by component.stateShowCompleteView.subscribeAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    var visibleState: Boolean by remember {
        mutableStateOf(false)
    }

    var visibleCompleteImportState: Boolean by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        visibleState = true
    }

    LaunchedEffect(stateShowCompleteView) {
        if(stateShowCompleteView) {
            visibleCompleteImportState = true
        }
    }

    DisposableEffect(component) {
        val listenerPassCreated: (message: String) -> Unit = { msg ->

            scope.launch {
                snackBarHostState.showSnackbar(msg)
            }
        }

        component.subscribeListenerEvent(listenerPassCreated)

        onDispose {
            component.unsubscribeListenerEvent(listenerPassCreated)
        }
    }

    val someActivityResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Обработка успешного выбора файла
                val selectedFileUri: Uri? = result.data?.data
                Log.d("manActiivty", "file $selectedFileUri")

                val contentResolver = context.contentResolver
                val inputStream: InputStream? = selectedFileUri?.let {
                    contentResolver.openInputStream(
                        it
                    )
                }

                if (inputStream != null) {

                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        stringBuilder.append(line)
                    }

                    val fileContent = stringBuilder.toString()
                    Log.d("manActiivty", "file content - $fileContent")
                    component.event(
                        ScreenImportPasswordEvent.ImportData(
                            fileContent,
                            DriverFactory(context = context)
                        )
                    )

                }
            }
        }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            if (component.stateBack) {
                UpBarButtonBack(onBackHandler = {
                    component.event(ScreenImportPasswordEvent.ClickBackButton)
                })
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (stateShowCompleteView) {
                    AnimatedVisibility(
                        visible = visibleCompleteImportState,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = LinearEasing
                            )
                        ),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                    ) {
                        Image(
                            modifier = Modifier.size(128.dp),
                            painter = painterResource(id = R.drawable.ic_complete),
                            contentDescription = "ic complete",
                            colorFilter = ColorFilter.tint(CustomColor().brandGreen)
                        )
                    }
                    AnimatedVisibility(
                        visible = visibleCompleteImportState,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = LinearEasing
                            )
                        ),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            text = "Import complete!",
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(32.dp))

                    AnimatedVisibility(
                        visible = visibleState,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = LinearEasing
                            )
                        ),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            text = "You can import your passwords, for this you need to provide permission",
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.size(32.dp))
                    AnimatedVisibility(
                        visible = visibleState,
                        enter = scaleIn(
                            animationSpec = tween(
                                durationMillis = 150,
                                easing = LinearEasing
                            )
                        ),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                    ) {
                        Button(
                            onClick = {
                                val intenst = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "application/json"
                                }
                                someActivityResultLauncher.launch(intenst)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                            colors = ButtonDefaults.buttonColors(CustomColor().brandBlueLight)
                        ) {
                            Text(
                                text = "Import",
                                style = MaterialTheme.typography.displayMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            MainComponentButton(
                "Next"
            ) { component.event(ScreenImportPasswordEvent.ClickButtonNext) }
        }
    }

}