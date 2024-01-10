package com.pass.word.session.utilits

import androidx.compose.runtime.Composable

@Composable
expect fun showToast(message: String)

fun String?.onCheckValidation (): String? {
    if(this == null) return null
    return this.ifEmpty { null }
}
