package com.pass.word.session.android.screen.viewComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.R
import com.pass.word.session.navigation.screen.main.detail.ScreenDetailEvent
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

@Composable
fun BoxItemCode(itemText: String) {
    Box(
        Modifier
            .size(48.dp)
            .border(2.dp,color = CustomColor().grayLight, RoundedCornerShape(600.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = itemText,
            color = Color.White, style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ButtonNumber(textButton: Int, clickHandler: (Int) -> Unit) {
    Box(
        Modifier
            .size(64.dp)
            .background(CustomColor().brandBlueLight, RoundedCornerShape(600.dp))
            .clickable { clickHandler(textButton) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun MainComponentButton(text: String, clickHandler: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(CustomColor().brandBlueLight)
            .clickable { clickHandler() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = text,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )
    }
}


@Composable
fun UpBarButtonBack(onBackHandler: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .clickable { onBackHandler() }
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            painter = painterResource(id = R.drawable.ic_arrow_back_nav),
            contentDescription = "Button back",
            colorFilter = ColorFilter.tint(
                Color.White
            )
        )
    }
}