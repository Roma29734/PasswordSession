package com.pass.word.session.android.screen.bottomScreen.addPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent.ScreenAddPasswordComponent
import com.pass.word.session.ui.CustomColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPasswordScreen(component: ScreenAddPasswordComponent) {

    var textEmailUserName by remember { mutableStateOf(TextFieldValue("")) }
    var textTitle by remember { mutableStateOf(TextFieldValue("")) }
    var textPassword by remember { mutableStateOf(TextFieldValue("")) }
    var textUrl by remember { mutableStateOf(TextFieldValue("")) }
    var textDescriptions by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            text = "Add New Password",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )


        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            text = "Title",
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
        OutlinedTextField(
            value = textTitle,
            onValueChange = { textTitle = it },
            textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // цвет при получении фокуса
                unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                cursorColor = Color.White
            )
        )

        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
            text = "Email/UserName",
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
        OutlinedTextField(
            value = textEmailUserName,
            onValueChange = { textEmailUserName = it },
            textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // цвет при получении фокуса
                unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                cursorColor = Color.White
            )
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
            text = "Password",
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
        OutlinedTextField(
            value = textPassword,
            onValueChange = { textPassword = it },
            textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // цвет при получении фокуса
                unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                cursorColor = Color.White
            )
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
            text = "Url",
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
        OutlinedTextField(
            value = textUrl,
            onValueChange = { textUrl = it },
            textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // цвет при получении фокуса
                unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                cursorColor = Color.White
            )
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
            text = "Descriptions",
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
        OutlinedTextField(
            value = textDescriptions,
            onValueChange = { textDescriptions = it },
            textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // цвет при получении фокуса
                unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                cursorColor = Color.White
            )
        )

    }
}