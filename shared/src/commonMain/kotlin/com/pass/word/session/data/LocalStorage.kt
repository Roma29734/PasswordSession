package com.pass.word.session.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

val settings: Settings = Settings()

internal const val keyAuthPass: String = "KEY_AUTH_PASS"

fun getParamsInt(key: String): Int? {
    return settings[key]
}

fun getParamsBoolean(key: String): Boolean? {
    return settings[key]
}

fun getParamsString(key: String): String? {
    return settings[key]
}

fun Int.putToParams(key: String) {
    settings.putInt(key, this)
}

fun Boolean.putToParams(key: String) {
    settings.putBoolean(key, this)
}

fun String.putToParams(key: String) {
    settings.putString(key, this)
}