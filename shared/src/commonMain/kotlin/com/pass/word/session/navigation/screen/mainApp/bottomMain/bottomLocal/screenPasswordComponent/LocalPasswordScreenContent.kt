package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent

import Img.MyIconPack
import Img.myiconpack.IcKeyVariantTow
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.ui.viewComponent.ItemPasswordView

@Composable
fun LocalPasswordScreenContent(
    listItemModel: List<PasswordItemModel>?,
    stateShowedIcon: Boolean,
    eventComponentDispatch: (model: PasswordItemModel) -> Unit,
) {

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
                            oncLick = { eventComponentDispatch(listItemModel!![countItem]) }
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
                    imageVector = MyIconPack.IcKeyVariantTow,
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