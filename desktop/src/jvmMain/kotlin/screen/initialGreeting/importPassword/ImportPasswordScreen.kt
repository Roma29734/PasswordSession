package screen.initialGreeting.importPassword

import Img.MyIconPack
import Img.myiconpack.IcComplete
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.awt.Frame
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun ImportPasswordScreen(component: ScreenImportPasswordComponent) {


    fun openFileDialog(): String? {
        val fileChooser = JFileChooser()
        val filter = FileNameExtensionFilter("JSON files", "json")
        fileChooser.fileFilter = filter
        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.selectedFile.absolutePath
        }
        return null
    }

    fun readFileContent(filePath: String): String? {
        val stringBuilder = StringBuilder()
        try {
            val reader = BufferedReader(FileReader(filePath))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append('\n')
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return stringBuilder.toString()
    }

    fun checkSelectedFile(filePath: String) {
        val fileContent = readFileContent(filePath)
        fileContent?.let { content ->
            println("Содержимое файла: $content")
            component.event(
                ScreenImportPasswordEvent.ImportData(
                    fileContent,
                    DriverFactory()
                )
            )
        }
    }

    fun checkSelectedFile() {
        val selectedFilePath = openFileDialog()
        selectedFilePath?.let { filePath ->
            println("Выбранный файл содержимое: ${checkSelectedFile(filePath)}")
        }
    }

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
                            imageVector = MyIconPack.IcComplete,
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
                                checkSelectedFile()
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
                "Next", true
            ) { component.event(ScreenImportPasswordEvent.ClickButtonNext) }
        }
    }
}