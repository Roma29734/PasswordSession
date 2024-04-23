package com.pass.word.session.utilits

import androidx.compose.runtime.Composable
import kotlinx.serialization.json.JsonObject
import java.io.File

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