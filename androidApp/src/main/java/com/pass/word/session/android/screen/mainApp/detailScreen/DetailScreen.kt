package com.pass.word.session.android.screen.mainApp.detailScreen

import android.annotation.SuppressLint
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailComponent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailEvent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(component: ScreenDetailComponent) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val stateOpenAlertDialog: Boolean by component.stateOpenAlertDialog.subscribeAsState()
    val context = LocalContext.current

    val itemModel: PasswordItemModel by component.passwordItem.subscribeAsState()

    component.getOneItem(DriverFactory(context = context))

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        AlertDialogDelete(openDialog = stateOpenAlertDialog, onBackHandler = {
            component.onEvent(ScreenDetailEvent.ChangeStateOpenedAlertDialog(false))
        }, onConfirmHandler = {
            component.onEvent(
                ScreenDetailEvent.DeleteItemPass(
                    databaseDriverFactory = DriverFactory(
                        context
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
                        painter = painterResource(id = R.drawable.ic_copy_sourse),
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
                        painter = painterResource(id = R.drawable.ic_edit),
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
                        painter = painterResource(id = R.drawable.ic_delete),
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
            painter = painterResource(id = R.drawable.ic_copy_sourse),
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