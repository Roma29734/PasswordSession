package com.pass.word.session.android.screen.viewComponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.CustomColor

@Composable
fun OutlineInputText(textInTitle: String, outText: String, onValueChangeHandler: (text: String) -> Unit, focusRequester: FocusRequester, onNextHandler: () -> Unit, keyboardType: KeyboardType) {
    Text(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        text = textInTitle,
        style = MaterialTheme.typography.displaySmall,
        color = CustomColor().grayLight
    )
    OutlinedTextField(
        value = outText,
        onValueChange = { onValueChangeHandler(it)},
        textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .focusRequester(focusRequester),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White, // цвет при получении фокуса
            unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNextHandler() }
        )

    )
}