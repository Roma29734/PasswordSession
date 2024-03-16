package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.tonPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.passwordScreen.ItemPasswordView
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.android.screen.viewComponent.ItemSelectedType
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.LoadingTonPassItemState
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordEvent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.StateSelectedType
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TonPasswordScreen(component: ScreenTonPasswordComponent) {
    val stateLoading by component.stateLoading.collectAsState()
    val context = LocalContext.current
    val listItemModel: List<PasswordItemModel>? by component.passwordListItem.collectAsState()
    val stateCallItem by component.stateCallItem.collectAsState()
    val stateSelectedTypeStorage by component.stateSelectedTypeStorage.collectAsState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(stateCallItem) {
        if (stateCallItem) {
            component.onEvent(ScreenTonPasswordEvent.ReadBdItem(DriverFactory(context)))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
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