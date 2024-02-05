package com.pass.word.session.utilits

import androidx.compose.runtime.Composable
import kotlinx.serialization.json.JsonObject

@Composable
actual fun showToast(message: String) {
//    val mContext = LocalContext.current
//    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    TODO()
}

actual fun createAndSaveJsonFile(context: Any, fileName: String, savedJson: JsonObject) {
    TODO()
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