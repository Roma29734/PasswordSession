package com.pass.word.session.utilits


actual fun generateAESKey(secretPhrase: String): ByteArray {
    TODO()
}


actual fun encrypt(text: String, secretPhrase: String): StateBasicResult<String> {
    TODO()
}


actual fun decrypt(encryptedText: String, secretPhrase: String): StateBasicResult<String> {
    TODO()
}
