package com.pass.word.session.android.screen.initialGreeting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.BoxItemCode
import com.pass.word.session.android.screen.viewComponent.ButtonNumber
import com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterInitialPassAuth.ScreenEnterInitialPassAuthComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterInitialPassAuth.ScreenEnterInitialPassAuthEvent

@Composable
fun EnterInitialPassAuthScreen(component: ScreenEnterInitialPassAuthComponent) {

    val passItem: String by component.passItem.subscribeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column {
            Image(
                modifier = Modifier
                    .clickable { component.onEvent(ScreenEnterInitialPassAuthEvent.ClickButtonBack) }
                    .padding(start = 16.dp, top = 8.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back_nav),
                contentDescription = "Button back",
                colorFilter = ColorFilter.tint(
                    Color.White
                )
            )

            Spacer(modifier = Modifier.size(48.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = "Enter the password to log in to the application. Attention, if you forget your password, you will not be able to restore it",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BoxItemCode(if (passItem.isNotEmpty()) "•" else "")
            BoxItemCode(if (passItem.length >= 2) "•" else "")
            BoxItemCode(if (passItem.length >= 3) "•" else "")
            BoxItemCode(if (passItem.length >= 4) "•" else "")
        }
        Row(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonNumber(1) {
                component.onEvent(ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(it))
            }
            ButtonNumber(2) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
            ButtonNumber(3) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonNumber(4) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
            ButtonNumber(5) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
            ButtonNumber(6) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonNumber(7) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
            ButtonNumber(8) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
            ButtonNumber(9) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                Modifier
                    .size(64.dp),
            )
            ButtonNumber(textButton = 0) {
                component.onEvent(
                    ScreenEnterInitialPassAuthEvent.StateUpdatePassItem(
                        it
                    )
                )
            }
            Box(
                Modifier
                    .size(64.dp),
            )
        }
    }
}