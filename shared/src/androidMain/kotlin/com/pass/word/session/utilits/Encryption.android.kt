package com.pass.word.session.utilits

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual fun generateAESKey(secretPhrase: String): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(secretPhrase.toByteArray(Charsets.UTF_8))
}


@RequiresApi(Build.VERSION_CODES.O)
actual fun encrypt(text: String, secretPhrase: String): StateBasicResult<String> {
    try {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(generateAESKey(secretPhrase), "AES")
        val ivParameterSpec = IvParameterSpec(secretPhrase.substring(0, 16).toByteArray(Charsets.UTF_8))
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec)
        val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        val result =  Base64.getEncoder().encodeToString(encryptedBytes)
        return StateBasicResult.InSuccess(result)
    } catch (e: javax.crypto.BadPaddingException) {
        return StateBasicResult.InError("${e.message}", ResponseCodState.CD403)
    } catch (e: Exception) {
        return StateBasicResult.InError("${e.message}", ResponseCodState.CD00)
    }
}
//

@RequiresApi(Build.VERSION_CODES.O)
actual fun decrypt(encryptedText: String, secretPhrase: String): StateBasicResult<String> {
    try {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(generateAESKey(secretPhrase), "AES")
        val ivParameterSpec = IvParameterSpec(secretPhrase.substring(0, 16).toByteArray(Charsets.UTF_8))
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec)
        val encryptedBytes = Base64.getDecoder().decode(encryptedText)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        val result = String(decryptedBytes, Charsets.UTF_8)
        return StateBasicResult.InSuccess(result)
    } catch (e: javax.crypto.BadPaddingException) {
        return StateBasicResult.InError("${e.message}", ResponseCodState.CD403)
    } catch (e: Exception) {
        return StateBasicResult.InError("${e.message}", ResponseCodState.CD00)
    }
}
