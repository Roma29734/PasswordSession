package com.pass.word.session.android.screen.bottomScreen.passwordScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.data.root.getDaysOrMonthsOrYearsDifference
import com.pass.word.session.navigation.screen.bottom.screenPasswordComponent.ScreenPasswordComponent
import com.pass.word.session.ui.CustomColor

@Composable
fun PasswordScreen(component: ScreenPasswordComponent) {
    val listItemModel: List<PasswordItemModel> by component.passwordListItem.subscribeAsState()
    val context = LocalContext.current
//    component.readBd(DriverFactory(context))1
    Log.d("passScreen", "listModel - $listItemModel")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            text = "Password",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        getDaysOrMonthsOrYearsDifference("06.01.2024")
        LazyColumn {
            items(count = listItemModel.size) { countItem ->
                ItemPasswordView(
                    nameItem = listItemModel[countItem].nameItemPassword,
                    emailItem = listItemModel[countItem].emailOrUserName,
                    changeData = listItemModel[countItem].changeData,
                    oncLick = {component.navigateToDetailEvent(listItemModel[countItem])}
                )
            }
        }
    }
}

@Composable
fun ItemPasswordView(nameItem: String, emailItem: String, changeData: String, oncLick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, bottom = 16.dp, end = 12.dp)
            .fillMaxWidth().clickable{oncLick()},
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(id = R.drawable.ic_password),
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