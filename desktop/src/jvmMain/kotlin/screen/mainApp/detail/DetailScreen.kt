package screen.mainApp.detail

import Img.MyIconPack
import Img.myiconpack.IcCopySourse
import Img.myiconpack.IcDelete
import Img.myiconpack.IcEdit
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.detail.AlertDialogDelete
import com.pass.word.session.navigation.screen.mainApp.detail.BaseTextItem
import com.pass.word.session.navigation.screen.mainApp.detail.Descriptions
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailComponent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomErrorDialog
import com.pass.word.session.ui.viewComponent.CustomLoadingDialog
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(component: ScreenDetailComponent) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val stateOpenAlertDialog: Boolean by component.stateOpenAlertDialog.subscribeAsState()
//    val context = LocalContext.current
    val stateLoading by component.stateOpenDialogChoseType.collectAsState()

    val itemModel: PasswordItemModel by component.passwordItem.subscribeAsState()

    component.getOneItem(DriverFactory())

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {



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
//                        component.onEvent(ScreenDetailEvent.HideDialog)
                    }
                )
            }
        }

        AlertDialogDelete(openDialog = stateOpenAlertDialog, onBackHandler = {
            component.onEvent(ScreenDetailEvent.ChangeStateOpenedAlertDialog(false))
        }, onConfirmHandler = {
            component.onEvent(
                ScreenDetailEvent.DeleteItemPass(
                    databaseDriverFactory = DriverFactory(

                    )
                )
            )
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                UpBarButtonBack(onBackHandler = {
                    component.onEvent(ScreenDetailEvent.ClickButtonBack)
                })
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
                    text = itemModel.nameItemPassword,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                BaseTextItem(
                    textTitle = "Email/Username",
                    textSubTitle = itemModel.emailOrUserName,
                    showSnacbarNandler = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Copy")
                        }
                    }
                )
                BaseTextItem(
                    textTitle = "Password",
                    textSubTitle = itemModel.passwordItem,
                    showSnacbarNandler = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Copy")
                        }
                    }
                )
                if (itemModel.urlSite != null) {
                    BaseTextItem(
                        textTitle = "Url",
                        textSubTitle = itemModel.urlSite.toString(),
                        showSnacbarNandler = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Copy")
                            }
                        }
                    )
                }
                if (itemModel.descriptions != null) {
                    Descriptions(
                        textTitle = "Descriptions",
                        textSubTitle = itemModel.descriptions.toString()
                    )
                }
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                        .clickable {
                            clipboardManager.setText(
                                AnnotatedString(
                                    ("${itemModel.nameItemPassword}:${itemModel.emailOrUserName}:${itemModel.passwordItem}${if (itemModel.urlSite != null) ":${itemModel.urlSite}" else ""}${if (itemModel.descriptions != null) ":${itemModel.descriptions}" else ""}")
                                )
                            )
                            scope.launch {
                                snackbarHostState.showSnackbar("Copy complete")
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = MyIconPack.IcCopySourse,
                        contentDescription = "icons copy full",
                        colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Copy everything",
                        style = MaterialTheme.typography.displayMedium,
                        color = CustomColor().brandBlueLight
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                        .clickable { component.onEvent(ScreenDetailEvent.EditItemPass) }
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = MyIconPack.IcEdit,
                        contentDescription = "icons edit",
                        colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.displayMedium,
                        color = CustomColor().brandBlueLight
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                        .clickable {
                            component.onEvent(ScreenDetailEvent.ChangeStateOpenedAlertDialog(true))
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = MyIconPack.IcDelete,
                        contentDescription = "icons edit",
                        colorFilter = ColorFilter.tint(CustomColor().brandRedMain)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.displayMedium,
                        color = CustomColor().brandRedMain
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

    }
}

