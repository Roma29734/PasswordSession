package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.passwordScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.R
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent.ScreenPasswordComponent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.ItemPasswordView

@Composable
fun PasswordScreen(component: ScreenPasswordComponent) {
    val listItemModel: List<PasswordItemModel>? by component.passwordListItem.collectAsState()
    val context = LocalContext.current
    val stateShowedIcon: Boolean by component.stateShowedIcon.subscribeAsState()

    Log.d("passScreen", "listModel - $listItemModel")

//    SideEffect {
    component.readBd(DriverFactory(context))
//    }

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


        if (!stateShowedIcon) {
            LazyColumn {
                if (listItemModel != null) {

                    items(count = listItemModel!!.size) { countItem ->
                        ItemPasswordView(
                            nameItem = listItemModel!![countItem].nameItemPassword,
                            emailItem = listItemModel!![countItem].emailOrUserName,
                            changeData = listItemModel!![countItem].changeData,
                            oncLick = { component.navigateToDetailEvent(listItemModel!![countItem]) }
                        )

                    }
                } else {
                    items(count = 0) {}
                }
            }
        } else {
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

