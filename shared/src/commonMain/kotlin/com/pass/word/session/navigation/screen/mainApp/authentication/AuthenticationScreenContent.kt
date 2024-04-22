package com.pass.word.session.navigation.screen.mainApp.authentication

import Img.MyIconPack
import Img.myiconpack.IcFingerPrint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.viewComponent.BoxItemCode
import com.pass.word.session.ui.viewComponent.ButtonNumber
import com.pass.word.session.ui.viewComponent.itemBoxToCode
import kotlinx.coroutines.FlowPreview

@Composable
fun AuthenticationScreenContent(
    passItem: String,
    context: Any?,
    eventComponentDispatch: (ScreenAuthStateEvent) -> Unit,
    handlerBtnFinger: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Enter your authentication code",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            itemBoxToCode(if (passItem.isNotEmpty()) passItem[0].toString() else "")
            itemBoxToCode(if (passItem.length >= 2) passItem[0].toString() else "")
            itemBoxToCode(if (passItem.length >= 3) passItem[0].toString() else "")
            itemBoxToCode(if (passItem.length >= 4) passItem[0].toString() else "")
        }
        Row(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonNumber(1) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
            ButtonNumber(2) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
            ButtonNumber(3) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
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
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
            ButtonNumber(5) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
            ButtonNumber(6) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
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
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
            ButtonNumber(8) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
            ButtonNumber(9) {
                eventComponentDispatch(
                    ScreenAuthStateEvent.StateUpdatePassItem(
                        it.toString(),
                        context
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if(handlerBtnFinger != null) {
                Box(
                    Modifier
                        .size(64.dp),
                )
                ButtonNumber(textButton = 0) {
                    eventComponentDispatch(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(), context
                        )
                    )
                }

                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 16.dp)
                        .clickable {
                            handlerBtnFinger()
                        },
                    imageVector = MyIconPack.IcFingerPrint,
                    contentDescription = "fingerPrintIco",
                    colorFilter = ColorFilter.tint(Color.White)
                )

            } else {
                ButtonNumber(textButton = 0) {
                    eventComponentDispatch(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(), context
                        )
                    )
                }
            }
        }
    }
}