package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent

import Img.MyIconPack
import Img.myiconpack.IcImport
import Img.myiconpack.IcLogoGitHub
import Img.myiconpack.IcLogoTelegram
import Img.myiconpack.IcPasswordVertical
import Img.myiconpack.IcPin
import Img.myiconpack.IcSecurityLock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.viewComponent.ItemSettingsMenu
import com.pass.word.session.ui.viewComponent.MainComponentButton

@Composable
fun SettingsScreenContent(
    itemSettingsList: List<ItemSettings>,
    eventComponentDispatch: (ScreenSettingsStateEvent) -> Unit,
    openUrlHandler: (text: String) -> Unit,
    mainComponentBtnHandler: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
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

            LazyColumn(content = {

                if (itemSettingsList.isNotEmpty()) {
                    items(count = itemSettingsList.size) { countItem ->

                        when (itemSettingsList[countItem]) {
                            ItemSettings.ImportPassword -> {
                                ItemSettingsMenu(
                                    image = MyIconPack.IcImport,
                                    text = "import password",
                                    clickHandler = {
                                        eventComponentDispatch(ScreenSettingsStateEvent.OnNavigateToImportPassword)
                                    }
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                            }

                            ItemSettings.ChangePassword -> {
                                ItemSettingsMenu(
                                    image = MyIconPack.IcPin,
                                    text = "change password",
                                    clickHandler = {
                                        eventComponentDispatch(ScreenSettingsStateEvent.OnNavigateToChangePasswordComponent)
                                    }
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                            }

                            ItemSettings.SeedPhraseSettings -> {
                                ItemSettingsMenu(
                                    image = MyIconPack.IcPasswordVertical,
                                    text = "seed phrase settings",
                                    clickHandler = {
                                        eventComponentDispatch(ScreenSettingsStateEvent.OnNavigateToSeedPhraseSettings)
                                    }
                                )

                                Spacer(modifier = Modifier.size(16.dp))
                            }

                            ItemSettings.GitHub -> {
                                ItemSettingsMenu(
                                    image = MyIconPack.IcLogoGitHub,
                                    text = "gitHub",
                                    clickHandler = {
                                        openUrlHandler(
                                            "https://github.com/Roma29734/PasswordSession",
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                            }

                            ItemSettings.Telegram -> {
                                ItemSettingsMenu(
                                    image = MyIconPack.IcLogoTelegram,
                                    text = "telegram",
                                    clickHandler = {
                                        openUrlHandler("https://t.me/apkPublicPrograms")
                                    }
                                )
                            }

                            ItemSettings.PassKeySettings -> {
                                ItemSettingsMenu(
                                    image = MyIconPack.IcSecurityLock,
                                    text = "pass key phrase settings",
                                    clickHandler = {
                                        eventComponentDispatch(ScreenSettingsStateEvent.OnNavigateToPassKeySettingsComponent)
                                    }
                                )

                                Spacer(modifier = Modifier.size(16.dp))
                            }
                        }

                    }
                }

            })

        }

        MainComponentButton(text = "download password", true) {
            mainComponentBtnHandler()
        }

    }
}