package com.pass.word.session.utilits

import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.JsonObject
import org.jetbrains.skiko.MainUIDispatcher
import java.io.File
import kotlin.coroutines.CoroutineContext

@Composable
actual fun showToast(message: String) {
//    val mContext = LocalContext.current
//    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    TODO()
}


actual fun createAndSaveJsonFile(context: Any, fileName: String, savedJson: JsonObject) {
    try {
        val file = File(System.getProperty("user.home"), "Downloads/$fileName")
        file.writeText(savedJson.toString())
        println("File saved successfully at: ${file.absolutePath}")
    } catch (e: Exception) {
        println("Error saving file: ${e.message}")
        e.printStackTrace()
    }
}

actual fun vibrationResponse(time: Int, context: Any) {
    TODO()
}

actual fun checkUseBiometric(
    context: Any,
    onAction: (successState: Boolean, message: String?) -> Unit,
) {

}

actual class Platform actual constructor() {
    actual val platform: String = "Desktop" // Укажите платформу явным образом
}

actual val defaultDispatcher: CoroutineContext
    get() =
        Dispatchers.Default


actual val mainDispatcher: CoroutineContext get() = MainUIDispatcher