package com.pass.word.session.navigation.screen.mainApp.screenEnterPass

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.viewComponent.ButtonNumber
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.ui.viewComponent.btnItemToEnterCodeAuth
import com.pass.word.session.ui.viewComponent.itemBoxToCode

@Composable
fun EnterPassScreenContent(
    passItem: String,
    passEnterState: Boolean,
    context: Any?,
    eventComponentDispatch: (ScreenEnterPassEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background).verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            UpBarButtonBack(onBackHandler = {
                eventComponentDispatch(ScreenEnterPassEvent.ClickButtonBack)
            })

            Spacer(modifier = Modifier.size(48.dp))

            if (!passEnterState) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth(),
                    text = "Enter the password to log in to the application. Attention, if you forget your password, you will not be able to restore it",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth(),
                    text = "Confirm the password you entered earlier",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                itemBoxToCode(if (passItem.isNotEmpty()) passItem[0].toString() else "")
                Spacer(Modifier.size(16.dp))
                itemBoxToCode(if (passItem.length >= 2) passItem[0].toString() else "")
                Spacer(Modifier.size(16.dp))
                itemBoxToCode(if (passItem.length >= 3) passItem[0].toString() else "")
                Spacer(Modifier.size(16.dp))
                itemBoxToCode(if (passItem.length >= 4) passItem[0].toString() else "")
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                btnItemToEnterCodeAuth(1) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it,
                            context
                        )
                    )
                }
                btnItemToEnterCodeAuth(2) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
                        )
                    )
                }
                btnItemToEnterCodeAuth(3) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
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
                btnItemToEnterCodeAuth(4) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
                        )
                    )
                }
                btnItemToEnterCodeAuth(5) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
                        )
                    )
                }
                btnItemToEnterCodeAuth(6) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
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
                btnItemToEnterCodeAuth(7) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
                        )
                    )
                }
                btnItemToEnterCodeAuth(8) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
                        )
                    )
                }
                btnItemToEnterCodeAuth(9) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
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
                btnItemToEnterCodeAuth(textButton = 0) {
                    eventComponentDispatch(
                        ScreenEnterPassEvent.StateUpdatePassItem(
                            it, context
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

}