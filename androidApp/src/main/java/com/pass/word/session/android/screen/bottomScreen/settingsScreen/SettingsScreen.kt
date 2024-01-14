package com.pass.word.session.android.screen.bottomScreen.settingsScreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.pass.word.session.android.R
import com.pass.word.session.navigation.screen.bottom.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.CoroutineScope

@Composable
fun SettingsScreen(component: ScreenSettingsComponent) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 24.dp),
                text = "Settings",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.size(16.dp))

            ItemSettingsMenu(
                image = painterResource(id = R.drawable.ic_security_lock),
                text = "change password",
                clickHandler = {}
            )
            Spacer(modifier = Modifier.size(16.dp))
            ItemSettingsMenu(
                image = painterResource(id = R.drawable.ic_logo_telegram),
                text = "telegram",
                clickHandler = {
                    openCustomTab("https://t.me/apkPublicPrograms", context)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            ItemSettingsMenu(
                image = painterResource(id = R.drawable.ic_logo_git_hub),
                text = "gitHub",
                clickHandler = {
                    openCustomTab("https://github.com/Roma29734/PasswordSession", context)
                }
            )
        }

        ItemSettingsDownloadMenu(clickHandler = {
            component.clickToButtonDownloadPass(context)
        })
    }
}


fun openCustomTab(url: String, context: Context) {
    val package_name = "com.android.chrome"

    val activity = (context as? Activity)
    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)

    val customBuilder = builder.build()

    // on below line we are checking if the package name is null or not.
    if (package_name != null) {
        customBuilder.intent.setPackage(package_name)

        customBuilder.launchUrl(context, Uri.parse(url))
    } else {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        activity?.startActivity(i)
    }
}

@Composable
fun ItemSettingsMenu(image: Painter, text: String, clickHandler: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { clickHandler() }
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = image,
            contentDescription = "image",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = text, style = MaterialTheme.typography.displayMedium, color = Color.White)
    }
}

@Composable
fun ItemSettingsDownloadMenu(clickHandler: () -> Unit) {

    Column(
        Modifier
            .fillMaxWidth()
            .background(CustomColor().brandBlueLight)
            .clickable { clickHandler() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "download password",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )
    }
}