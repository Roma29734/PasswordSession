package com.pass.word.session.utilits

import androidx.compose.runtime.Composable
import kotlinx.serialization.json.JsonObject

@Composable
actual fun showToast(message: String) {
    TODO()
}
//
//actual fun checkWriteExternalStoragePermission(context: Any): Boolean {
//    TODO()
//}
//
//actual fun requestWriteExternalStoragePermission(activity: Any, requestCode: Int) {
//    TODO()
//}
//
actual fun createAndSaveJsonFile(context: Any, fileName: String, savedJson: JsonObject) {
    TODO()
}


actual fun vibrationResponse(time: Int, context: Any) {

}


actual fun checkUseBiometric(
    context: Any,
    onAction: (successState: Boolean, message: String?) -> Unit,
) {
    TODO()
}

actual class Platform actual constructor() {
    actual val platform: String = "IOS" // Укажите платформу явным образом
}