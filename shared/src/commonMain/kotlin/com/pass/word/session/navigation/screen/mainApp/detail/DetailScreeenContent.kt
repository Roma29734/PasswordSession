package com.pass.word.session.navigation.screen.mainApp.detail

import Img.MyIconPack
import Img.myiconpack.IcCopySourse
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.CustomColor

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