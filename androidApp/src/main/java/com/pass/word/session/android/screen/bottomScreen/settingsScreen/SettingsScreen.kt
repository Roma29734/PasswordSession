package com.pass.word.session.android.screen.bottomScreen.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pass.word.session.navigation.screen.bottom.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.CoroutineScope


@Composable
fun SettingsScreen(component: ScreenSettingsComponent) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()


    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            text = "Settings",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        ItemSettingsDownloadMenu(clickHandler = {
            component.clickToButtonDownloadPass(context)
        })
    }
}


@Composable
fun ItemSettingsDownloadMenu(clickHandler: () -> Unit) {

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
            text = "download password", style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )
    }
}