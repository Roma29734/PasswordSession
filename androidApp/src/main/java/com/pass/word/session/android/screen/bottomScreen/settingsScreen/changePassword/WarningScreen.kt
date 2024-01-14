package com.pass.word.session.android.screen.bottomScreen.settingsScreen.changePassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.navigation.screen.main.changePassword.ScreenWarningComponent
import com.pass.word.session.ui.CustomColor

@Composable
fun WarningScreen(component: ScreenWarningComponent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .clickable { component.onBackNavigate() }
                    .padding(start = 16.dp, top = 8.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back_nav),
                contentDescription = "Button back",
                colorFilter = ColorFilter.tint(
                    Color.White
                )
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_warning),
            contentDescription = "ic warning",
            modifier = Modifier
                .size(190.dp)
                .padding(top = 64.dp),
            colorFilter = ColorFilter.tint(CustomColor().brandRedMain)
        )

        Text(
            text = "warning, if you forget your password, you will not be able to reset it, that is, you will lose all your data, treat the next step with care, by clicking continue you agree that all your data may be lost beyond recovery",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center
        )

        MainComponentButton(text = "continue") { component.onNextNavigate() }

    }
}