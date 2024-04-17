package com.pass.word.session.ui.viewComponent

import Img.MyIconPack
import Img.myiconpack.IcArrowBackNav
import Img.myiconpack.IcDownMore
import Img.myiconpack.IcPassword
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(itemText) {
        isVisible.value = itemText == "•"
    }

    Box(
        Modifier
            .size(48.dp)
            .border(2.dp, color = CustomColor().grayLight, RoundedCornerShape(600.dp)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible.value,
            enter = fadeIn(animationSpec = TweenSpec(durationMillis = 300)),
            exit = fadeOut(animationSpec = TweenSpec(durationMillis = 300))
        ) {
            Text(
                text = itemText,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
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
            imageVector = MyIconPack.IcArrowBackNav,
            contentDescription = "Button back",
            colorFilter = ColorFilter.tint(
                Color.White
            )
        )
    }
}


@Composable
fun InputSeedContainer(
    textItem: String,
    focusRequesterTitle: FocusRequester,
    onNextHandler: () -> Unit,
    changeTextItemHandler: (itemText: String) -> Unit
) {
    OutlinedTextField(
        value = textItem,
        textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
        onValueChange = { changeTextItemHandler(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
            .focusRequester(focusRequesterTitle),
        keyboardActions = KeyboardActions(onNext = {
            onNextHandler()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White, // цвет при получении фокуса
            unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
        ),
    )
}

@Composable
fun ItemPasswordView(nameItem: String, emailItem: String, changeData: String, oncLick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, bottom = 16.dp, end = 12.dp)
            .fillMaxWidth()
            .clickable { oncLick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = MyIconPack.IcPassword,
                contentDescription = null,
                colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
            )
            Column {
                Text(
                    text = nameItem,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
                Text(
                    text = emailItem,
                    style = MaterialTheme.typography.displaySmall,
                    color = CustomColor().grayLight
                )
            }
        }
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = changeData,
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
    }
}

@Composable
fun ItemPhrase(text: String) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 64.dp, end = 64.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(16.dp),
        border = BorderStroke(2.dp, CustomColor().grayLight),
    ) {
        Row(
            modifier = Modifier
                .background(CustomColor().mainBlue)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
            )
        }
    }
}

data class ItemSettingsMenuModel(
    val image: ImageVector, val text: String, val clickHandler: () -> Unit
)

@Composable
fun ItemSettingsMenu(image: ImageVector, text: String, clickHandler: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { clickHandler() }
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = image,
            contentDescription = "image",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = text, style = MaterialTheme.typography.displayMedium, color = Color.White)
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
                imageVector = MyIconPack.IcDownMore,
                contentDescription = "ic_more",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}