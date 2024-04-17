package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.tonPassword

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.CustomErrorDialog
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.android.screen.viewComponent.ItemSelectedType
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.ItemPasswordView
import com.pass.word.session.utilits.StateBasicDialog
import com.pass.word.session.utilits.StatePassItemDisplay
import com.pass.word.session.utilits.StateSelectedType
import com.pass.word.session.utilits.StateStatusBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TonPasswordScreen(component: ScreenTonPasswordComponent) {
    val stateLoading by component.stateLoading.collectAsState()
    val context = LocalContext.current
    val statePassItemDisplay by component.statePassItemDisplay.collectAsState()
    val stateCallItem by component.stateCallItem.collectAsState()
    val stateSelectedTypeStorage by component.stateSelectedTypeStorage.collectAsState()
    val stateVisibleStatusBar by component.stateVisibleStatusBar.collectAsState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(stateCallItem) {
        if (stateCallItem) {
            component.onEvent(ScreenTonPasswordEvent.ReadBdItem(DriverFactory(context)))
        }
        component.onEvent(ScreenTonPasswordEvent.ReadCashPass(DriverFactory(context)))
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            component.onEvent(ScreenTonPasswordEvent.ReadBdItem(DriverFactory(context)))
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
                text = "Password",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            ItemSelectedType(
                modifier = Modifier.padding(end = 16.dp),
                textItem = if (stateSelectedTypeStorage == StateSelectedType.TonStorage) "tonStorage" else "localStorage",
                colorButton = CustomColor().brandBlueLight
            ) {
                openBottomSheet = true
            }
        }


        if (stateVisibleStatusBar is StateStatusBar.Show) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CustomColor().brandRedMain),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = (stateVisibleStatusBar as StateStatusBar.Show).message,
                    style = MaterialTheme.typography.bodyMedium.plus(TextStyle(fontSize = 16.sp)),
                    color = Color.White
                )
            }
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    scope.launch {
                        openBottomSheet = false
                    }
                },
                containerColor = CustomColor().mainBlue
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier.padding(start = 32.dp, top = 8.dp, bottom = 8.dp),
                        text = "Types of storage",
                        style = MaterialTheme.typography.bodyLarge.plus(TextStyle(fontSize = 18.sp)),
                        color = Color.White
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                            .clickable {
                                if (stateSelectedTypeStorage == StateSelectedType.TonStorage) {
                                    openBottomSheet = false
                                } else {
                                    component.onEvent(
                                        ScreenTonPasswordEvent.UpdateSelectedType(
                                            DriverFactory(context),
                                            StateSelectedType.TonStorage
                                        )
                                    )
                                    openBottomSheet = false
                                }
                            },
                        elevation = CardDefaults.cardElevation(64.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(if (stateSelectedTypeStorage == StateSelectedType.TonStorage) CustomColor().brandBlueLight else CustomColor().accentDark)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                                text = "tonStorage",
                                style = MaterialTheme.typography.displayMedium,
                                color = Color.White
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 32.dp, bottom = 32.dp)
                            .clickable {
                                if (stateSelectedTypeStorage == StateSelectedType.LocalStorage) {
                                    openBottomSheet = false
                                } else {
                                    component.onEvent(
                                        ScreenTonPasswordEvent.UpdateSelectedType(
                                            DriverFactory(context),
                                            StateSelectedType.LocalStorage
                                        )
                                    )
                                    openBottomSheet = false
                                }
                            },
                        elevation = CardDefaults.cardElevation(64.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(if (stateSelectedTypeStorage == StateSelectedType.LocalStorage) CustomColor().brandBlueLight else CustomColor().accentDark)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                                text = "localStorage",
                                style = MaterialTheme.typography.displayMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        if (statePassItemDisplay is StatePassItemDisplay.VisibleItem) {
            val item = (statePassItemDisplay as StatePassItemDisplay.VisibleItem).passItem
            if (stateSelectedTypeStorage == StateSelectedType.TonStorage) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        isRefreshing = true
                    },
                ) {
                    LazyColumn(Modifier.padding(top = 8.dp)) {
                        if (item != null) {
                            items(count = item.size) { countItem ->
                                ItemPasswordView(
                                    nameItem = item[countItem].nameItemPassword,
                                    emailItem = item[countItem].emailOrUserName,
                                    changeData = item[countItem].changeData,
                                    oncLick = {
                                        component.onEvent(ScreenTonPasswordEvent.ClickToItem(item[countItem]))
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                LazyColumn(Modifier.padding(top = 8.dp)) {
                    if (item != null) {
                        items(count = item.size) { countItem ->
                            ItemPasswordView(
                                nameItem = item[countItem].nameItemPassword,
                                emailItem = item[countItem].emailOrUserName,
                                changeData = item[countItem].changeData,
                                oncLick = {
                                    component.onEvent(ScreenTonPasswordEvent.ClickToItem(item[countItem]))
                                }
                            )
                        }
                    }
                }
            }
        }

        if (statePassItemDisplay is StatePassItemDisplay.VisibleMessage) {
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
                    text = (statePassItemDisplay as StatePassItemDisplay.VisibleMessage).message,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (stateLoading is StateBasicDialog.Show) {
            Dialog(onDismissRequest = { stateLoading as StateBasicDialog.Show }) {
                CustomLoadingDialog()
            }
        }

        if (stateLoading is StateBasicDialog.Error) {
            Dialog(onDismissRequest = { stateLoading is StateBasicDialog.Error }) {
                CustomErrorDialog(
                    textTitle = "An error has occurred",
                    textSubTitle = "An error occurred during the execution of the request. try again later",
                    textButton = "close",
                    handlerButton = {
                        component.onEvent(ScreenTonPasswordEvent.HideDialog)
                    }
                )
            }
        }
    }
}