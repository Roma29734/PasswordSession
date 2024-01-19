package com.pass.word.session.utilits

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.File
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


@Composable
actual fun showToast(message: String) {
    val mContext = LocalContext.current
    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.Q)
actual fun createAndSaveJsonFile(context: Any, fileName: String, savedJson: JsonObject) {
    try {

        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, fileName)

        file.writeText(Json.encodeToString(JsonObject.serializer(), savedJson))

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = (context as Context).contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)

        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(
                    Json.encodeToString(JsonObject.serializer(), savedJson).toByteArray()
                )
            }
            Log.d("createAndSaveJsonFile", "complete")
        }

    } catch (e: Exception) {
        Log.d("createAndSaveJsonFile", "${e.message}")
        e.printStackTrace()
    }
}






