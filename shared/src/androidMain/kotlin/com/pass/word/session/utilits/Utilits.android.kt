package com.pass.word.session.utilits

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.ComponentContext

@Composable
actual fun showToast(message: String) {
    val mContext = LocalContext.current
    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
}