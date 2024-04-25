package screen.initialGreeting.importPassword


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
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ImportPasswordScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordEvent
import kotlinx.coroutines.launch
import java.io.BufferedReader
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
            stateShowCompleteView =stateShowCompleteView,
            stateBack = component.stateBack,
            visibleState = visibleState,
            visibleCompleteImportState = visibleCompleteImportState,
            checkSelectedFile = {
                checkSelectedFile()
            },
            eventComponentDispatch = {component.event(it)}
        )
    }
}