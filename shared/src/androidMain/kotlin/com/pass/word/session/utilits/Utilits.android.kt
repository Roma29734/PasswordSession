@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.pass.word.session.utilits

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.content.ContentValues
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.File
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


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

actual fun vibrationResponse(time: Int, context: Any) {
    val contextResolver = (context as Context)
    val vibrator = contextResolver.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(time.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(time.toLong())
    }
}


actual fun checkUseBiometric(
    context: Any,
    onAction: (successState: Boolean, message: String?) -> Unit,
){
    try {
        val contextRealy = (context as Context)
        var cancellationSignal: CancellationSignal? = null

        fun notifyUser(message: String) {
            Log.d("BIOMETRIC", message)
        }

        fun getCancelletionSignal(): CancellationSignal {
            cancellationSignal = CancellationSignal()
            cancellationSignal?.setOnCancelListener {
                notifyUser("Ath Cancelled via Signal")
            }

            return cancellationSignal as CancellationSignal
        }


        val authenticationCalBack =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        onAction(false, "Authentication Error $errorCode")
                        notifyUser("Authentication Error $errorCode")
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                        super.onAuthenticationHelp(helpCode, helpString)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        notifyUser("Authentication Succeeded")
                        onAction(true, null)
                        super.onAuthenticationSucceeded(result)
                    }
                }
            } else {
//                object : FingerprintManagerCompat.AuthenticationCallback() {
//                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
//                        onAction(false, "Authentication Error $errorCode")
//                        notifyUser("Authentication Error $errorCode")
//                        super.onAuthenticationError(errorCode, errString)
//                    }
//
//                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
//                        super.onAuthenticationHelp(helpCode, helpString)
//                    }
//
//                    override fun onAuthenticationFailed() {
//                        super.onAuthenticationFailed()
//                    }
//                }
            }

//        val authenticationCalBackFinger: FingerprintManagerCompat.AuthenticationCallback =
//            object : FingerprintManagerCompat.AuthenticationCallback() {
//                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
//                    onAction(false, "Authentication Error $errorCode")
//                    notifyUser("Authentication Error $errorCode")
//                    super.onAuthenticationError(errorCode, errString)
//                }
//
//                override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
//                    super.onAuthenticationHelp(helpCode, helpString)
//                }
//
//                override fun onAuthenticationFailed() {
//                    super.onAuthenticationFailed()
//                }
//            }

        fun checkBiometricSupport(context: Context): Boolean {
            val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (!keyguardManager.isDeviceSecure) {
                onAction(false, "Lock screen security not enabled in the settings")
                notifyUser("Lock screen security not enabled in the settings")
                return false
            }

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.USE_BIOMETRIC
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notifyUser("Fingerprint authentication permission not enabled")
                return false
            }

            return context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
        }



        fun launchBiometrics() {
            if (checkBiometricSupport(contextRealy)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val biometricPrompt = BiometricPrompt
                        .Builder(contextRealy)
                        .setTitle("Authentication is required")
                        .setSubtitle("Password Session")
                        .setNegativeButton("Not Now", contextRealy.mainExecutor) { dialogInterface, i ->
                            notifyUser("Authentication cancelled")
                        }
                        .build()

                    if(authenticationCalBack is BiometricPrompt.AuthenticationCallback) {
                        biometricPrompt.authenticate(
                            getCancelletionSignal(),
                            contextRealy.mainExecutor,
                            authenticationCalBack
                        )
                    }
                } else {
//                    val fingerprintManager = FingerprintManagerCompat.from(contextRealy)
//                    if (fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()) {
//                        val cancellationSignal = androidx.core.os.CancellationSignal()
//                        if (ActivityCompat.checkSelfPermission(contextRealy, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
//                            notifyUser("Fingerprint authentication permission not granted")
//                            return
//                        }
//                        fingerprintManager.authenticate(null, 0, cancellationSignal, authenticationCalBackFinger, null)
//                    }
                }
            }
        }

        fun launchBiometric() {
            if (checkBiometricSupport(contextRealy)) {
                val biometricPrompt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    BiometricPrompt
                        .Builder(contextRealy)
                        .setTitle("Authentication is required")
                        .setSubtitle("Password Session")
                        .setNegativeButton("Not Now", contextRealy.mainExecutor) { dialogInterface, i ->
                            notifyUser("Authentication cancelled")
                        }
                        .build()
                } else {
//                    val fingerprintManager = FingerprintManagerCompat.from(contextRealy)
//                    if (fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()) {
//                        val cancellationSignal = androidx.core.os.CancellationSignal()
//                        if (ActivityCompat.checkSelfPermission(contextRealy, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
//                            notifyUser("Fingerprint authentication permission not granted")
//                            return
//                        }
//                        fingerprintManager.authenticate(null, 0, cancellationSignal, authenticationCalBackFinger, null)
//                    } else {
                        // Fingerprint authentication is not available, handle accordingly
                        // Notify the user or fall back to another authentication method
//                    }
                    TODO("VERSION.SDK_INT < P")
                }
                if(authenticationCalBack is BiometricPrompt.AuthenticationCallback) {
                    biometricPrompt.authenticate(
                        getCancelletionSignal(),
                        contextRealy.mainExecutor,
                        authenticationCalBack
                    )
                }
            }
        }
        launchBiometrics()
    } catch (e: Exception) {

    }
}
actual class Platform actual constructor() {
    actual val platform: String = "Android" // Укажите платформу явным образом
}


actual val defaultDispatcher: CoroutineContext
    get() =
        Dispatchers.Default


actual val mainDispatcher: CoroutineContext get() = Dispatchers.Main

