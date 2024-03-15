package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.tonPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.passwordScreen.ItemPasswordView
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.LoadingTonPassItemState
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordEvent
import com.pass.word.session.ui.CustomColor

@Composable
fun TonPasswordScreen(component: ScreenTonPasswordComponent) {
    val stateLoading by component.stateLoading.collectAsState()
    val context = LocalContext.current
    val listItemModel: List<PasswordItemModel>? by component.passwordListItem.collectAsState()
    val stateCallItem by component.stateCallItem.collectAsState()

    LaunchedEffect(stateCallItem) {
        if(stateCallItem) {
            component.onEvent(ScreenTonPasswordEvent.ReadBdItem(DriverFactory(context)))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            text = "Password",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        LazyColumn {
            if (listItemModel != null) {

                items(count = listItemModel!!.size) { countItem ->
                    ItemPasswordView(
                        nameItem = listItemModel!![countItem].nameItemPassword,
                        emailItem = listItemModel!![countItem].emailOrUserName,
                        changeData = listItemModel!![countItem].changeData,
                        oncLick = { }
                    )
                }
            } else {
                items(count = 0) {}
            }
        }

        when (stateLoading) {

            is LoadingTonPassItemState.IsSuccess -> {

            }

            is LoadingTonPassItemState.InLoading -> {
                Dialog(onDismissRequest = { stateLoading as LoadingTonPassItemState.InLoading }) {
                    CustomLoadingDialog()
                }
            }

            is LoadingTonPassItemState.InEmpty -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(250.dp),
                        painter = painterResource(id = R.drawable.key_variant_tow),
                        contentDescription = "iconstEmpty notes",
                    )

                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = "You don't have any saved passwords",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            is LoadingTonPassItemState.InError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(250.dp),
                        painter = painterResource(id = R.drawable.key_variant_tow),
                        contentDescription = "iconstEmpty notes",
                    )

                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = "You don't have any saved passwords",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

