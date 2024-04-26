package com.pass.word.session.navigation.screen.mainApp.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomErrorDialog
import com.pass.word.session.ui.viewComponent.CustomLoadingDialog
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.launch

@Composable
fun DetailScreenContent(
    clipboardManager: ClipboardManager,
    stateOpenAlertDialog: Boolean,
    stateLoading: StateBasicDialog,
    itemModel: PasswordItemModel,
    driverFactory: DriverFactory,
    snackBarHostHandler: (String) -> Unit,
    eventComponentDispatch: (ScreenDetailEvent) -> Unit,
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
                    eventComponentDispatch(ScreenDetailEvent.HideDialog)
                }
            )
        }
    }

    AlertDialogDelete(openDialog = stateOpenAlertDialog, onBackHandler = {
        eventComponentDispatch(ScreenDetailEvent.ChangeStateOpenedAlertDialog(false))
    }, onConfirmHandler = {
        eventComponentDispatch(
            ScreenDetailEvent.DeleteItemPass(
                databaseDriverFactory = driverFactory
            )
        )
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background).verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            UpBarButtonBack(onBackHandler = {
                eventComponentDispatch(ScreenDetailEvent.ClickButtonBack)
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
                    snackBarHostHandler("Copy")
                }
            )
            BaseTextItem(
                textTitle = "Password",
                textSubTitle = itemModel.passwordItem,
                showSnacbarNandler = {

                    snackBarHostHandler("Copy")

                }
            )
            if (itemModel.urlSite != null) {
                BaseTextItem(
                    textTitle = "Url",
                    textSubTitle = itemModel.urlSite.toString(),
                    showSnacbarNandler = {

                        snackBarHostHandler("Copy")

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

                        snackBarHostHandler("Copy complete")

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
                    .clickable { eventComponentDispatch(ScreenDetailEvent.EditItemPass) },
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
                        eventComponentDispatch(ScreenDetailEvent.ChangeStateOpenedAlertDialog(true))
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


@Composable
fun BaseTextItem(textTitle: String, textSubTitle: String, showSnacbarNandler: () -> Unit) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Row(
        modifier = Modifier
            .padding(start = 12.dp, bottom = 16.dp, end = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Column {
            Text(
                text = textTitle,
                style = MaterialTheme.typography.displaySmall,
                color = CustomColor().grayLight
            )
            Text(
                text = textSubTitle,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
        }

        Image(
            modifier = Modifier.clickable {
                clipboardManager.setText(AnnotatedString((textSubTitle)))
                showSnacbarNandler()
            },
            imageVector = MyIconPack.IcCopySourse,
            contentDescription = "Button copy",
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun Descriptions(textTitle: String, textSubTitle: String) {
    Column(modifier = Modifier.padding(start = 12.dp, bottom = 16.dp, end = 12.dp)) {
        Text(
            text = textTitle,
            style = MaterialTheme.typography.displaySmall,
            color = CustomColor().grayLight
        )
        Text(
            text = textSubTitle,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )
    }
}

@Composable
fun AlertDialogDelete(
    openDialog: Boolean,
    onBackHandler: () -> Unit,
    onConfirmHandler: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            // on dialog dismiss we are setting
            // our dialog value to false.
            onDismissRequest = { onBackHandler() },

            // below line is use to display title of our dialog
            // box and we are setting text color to white.
            title = { Text(text = "Delete the password?", color = Color.White) },

            // below line is use to display
            // description to our alert dialog.
            text = {
                Text(
                    "Are you sure you want to delete the password? It will be impossible to restore it",
                    color = Color.White
                )
            },

            // in below line we are displaying
            // our confirm button.
            confirmButton = {
                // below line we are adding on click
                // listener for our confirm button.
                TextButton(
                    onClick = {
                        onBackHandler()
                        onConfirmHandler()
                    }
                ) {
                    // in this line we are adding
                    // text for our confirm button.
                    Text("Delete", color = Color.White)
                }
            },
            // in below line we are displaying
            // our dismiss button.
            dismissButton = {
                // in below line we are displaying
                // our text button
                TextButton(
                    // adding on click listener for this button
                    onClick = {
                        onBackHandler()
                    }
                ) {
                    // adding text to our button.
                    Text("Cancel", color = Color.White)
                }
            },
            containerColor = CustomColor().brandBlueLight,
        )
    }
}