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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pass.word.session.android.R
import com.pass.word.session.ui.CustomColor

@Composable
fun OutlineInputText(
    textInTitle: String,
    outText: String,
    onValueChangeHandler: (text: String) -> Unit,
    focusRequester: FocusRequester,
    onNextHandler: () -> Unit,
    keyboardType: KeyboardType
) {
    Text(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        text = textInTitle,
        style = MaterialTheme.typography.displaySmall,
        color = CustomColor().grayLight
    )
    OutlinedTextField(
        value = outText,
        onValueChange = { onValueChangeHandler(it) },
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
            .border(2.dp, color = CustomColor().grayLight, RoundedCornerShape(600.dp)),
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
fun MainComponentButton(
    text: String,
    enabledState: Boolean,
    colorButton: Color = CustomColor().brandBlueLight,
    clickHandler: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(if (enabledState) colorButton else CustomColor().grayLight)
            .clickable(enabledState) { clickHandler() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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


@Composable
fun ItemSelectedType(
    modifier: Modifier = Modifier,
    textItem: String,
    colorButton: Color,
    handlerClick: () -> Unit,
) {
    Card(
        modifier = modifier.clickable { handlerClick() },
        elevation = CardDefaults.cardElevation(64.dp)
    ) {
        Row(
            modifier = Modifier.background(colorButton),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = textItem,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp),
                style = MaterialTheme.typography.bodyMedium.plus(TextStyle(fontSize = 14.sp)),
                color = Color.White
            )

            Image(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 16.dp),
                painter = painterResource(id = R.drawable.ic_down_more),
                contentDescription = "ic_more",
                colorFilter = ColorFilter.tint(Color.White)
            )

        }
    }
}