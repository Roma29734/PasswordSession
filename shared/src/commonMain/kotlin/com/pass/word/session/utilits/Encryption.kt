package com.pass.word.session.utilits



expect fun generateAESKey(secretPhrase: String): ByteArray
//    val digest = MessageDigest.getInstance("SHA-256")
//    return digest.digest(secretPhrase.toByteArray(Charsets.UTF_8))


expect fun encrypt(text: String, secretPhrase: String): String
//    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//    val keySpec = SecretKeySpec(generateAESKey(secretPhrase), "AES")
//    val ivParameterSpec = IvParameterSpec(secretPhrase.substring(0, 16).toByteArray(Charsets.UTF_8))
//    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec)
//    val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
//    return Base64.getEncoder().encodeToString(encryptedBytes)
//

expect fun decrypt(encryptedText: String, secretPhrase: String): String
//    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//    val keySpec = SecretKeySpec(generateAESKey(secretPhrase), "AES")
//    val ivParameterSpec = IvParameterSpec(secretPhrase.substring(0, 16).toByteArray(Charsets.UTF_8))
//    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec)
//    val encryptedBytes = Base64.getDecoder().decode(encryptedText)
//    val decryptedBytes = cipher.doFinal(encryptedBytes)
//    return String(decryptedBytes, Charsets.UTF_8)
