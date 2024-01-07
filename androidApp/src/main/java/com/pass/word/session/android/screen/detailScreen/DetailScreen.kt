package com.pass.word.session.android.screen.detailScreen

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.core.content.ContextCompat
import com.pass.word.session.android.R
import com.pass.word.session.navigation.screen.main.detail.ScreenDetailComponent
import com.pass.word.session.navigation.screen.main.detail.ScreenDetailEvent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.coroutines.coroutineContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(component: ScreenDetailComponent) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Image(
                modifier = Modifier
                    .clickable { component.onEvent(ScreenDetailEvent.ClickButtonBack) }
                    .padding(start = 16.dp, top = 8.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back_nav),
                contentDescription = "Button back",
                colorFilter = ColorFilter.tint(
                    Color.White
                )
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
                text = component.passDetailModel.nameItemPassword,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            BaseTextItem(
                textTitle = "Email/Username",
                textSubTitle = component.passDetailModel.emailOrUserName,
                showSnacbarNandler = {scope.launch {
                    snackbarHostState.showSnackbar("Copy")
                }}
            )
            BaseTextItem(
                textTitle = "Password",
                textSubTitle = component.passDetailModel.passwordItem,
                showSnacbarNandler = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Copy")
                    }
                }
            )
            if (component.passDetailModel.urlSite != null) {
                BaseTextItem(
                    textTitle = "Url",
                    textSubTitle = component.passDetailModel.urlSite.toString(),
                    showSnacbarNandler = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Copy")
                        }
                    }
                )
            }
            if (component.passDetailModel.descriptions != null) {
                Descriptions(
                    textTitle = "Descriptions",
                    textSubTitle = component.passDetailModel.descriptions.toString()
                )
            }
        }

    }
}


@Composable
fun BaseTextItem(textTitle: String, textSubTitle: String, showSnacbarNandler: () -> Unit) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val mContext = LocalContext.current
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