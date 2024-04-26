package com.pass.word.session.android.screen.initialGreeting

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ImportPasswordScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordEvent
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
    val activity = LocalContext.current as Activity
    val stateShowCompleteView: Boolean by component.stateShowCompleteView.subscribeAsState()
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

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("papersFragment", "получено if")
                // Разрешение получено, выполните необходимые действия здесь
//            save()
                val intenst = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/json"
                }
                someActivityResultLauncher.launch(intenst)
            } else {
                Log.d("papersFragment", "не получено if")
                Toast.makeText(context, "Предоставьте разрешение", Toast.LENGTH_SHORT)
                    .show()
            }
        }

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
        if (stateShowCompleteView) {
            visibleCompleteImportState = true
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
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        ImportPasswordScreenContent(
            stateShowCompleteView = stateShowCompleteView,
            stateBack = component.stateBack,
            visibleState = visibleState,
            visibleCompleteImportState = visibleCompleteImportState,
            checkSelectedFile = {
                requestWriteStoragePermission(
                    context = context,
                    activity = activity,
                    launch = {
                        launcher.launch(it)
                    },
                    funHandler = {
                        val intenst = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "application/json"
                        }
                        someActivityResultLauncher.launch(intenst)
                    }
                )
            },
            eventComponentDispatch = { component.event(it) }
        )
    }
}


private fun requestWriteStoragePermission(
    context: Context,
    activity: Activity,
    launch: (String) -> Unit,
    funHandler: () -> Unit
) {
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        funHandler()
        return
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            funHandler()
        }
    } else {

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
        }
    }
}