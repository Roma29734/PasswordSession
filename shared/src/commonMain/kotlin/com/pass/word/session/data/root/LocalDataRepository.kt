package com.pass.word.session.data.root

import com.pass.word.session.data.model.PasswordItemModel

class LocalDataRepository {

    fun getPasswordItem(): List<PasswordItemModel> {

        // this is for example result
        return listOf<PasswordItemModel>(
            PasswordItemModel(
                nameItemPassword = "GitHub",
                emailOrUserName = "git_hub@gmail.com",
                passwordItem = "12345678",
                urlSite = "https://gitHub.com",
                descriptions = "its password from gitHub",
                changeData = "06.01.2024",
                id = 1,
            ),
            PasswordItemModel(
                nameItemPassword = "Google",
                emailOrUserName = "goolge@gmail.com",
                passwordItem = "12345678",
                urlSite = "https://goolge.com",
                descriptions = "its password from google",
                changeData = "01.01.2024",
                id = 2,
            ),
            PasswordItemModel(
                nameItemPassword = "Telegram",
                emailOrUserName = "telegram@gmail.com",
                passwordItem = "passwordForTelegram12345678",
                urlSite = "https://telegra.com",
                descriptions = "Its password form telegram",
                changeData = "05.12.2023",
                id = 3,
            ),
            PasswordItemModel(
                nameItemPassword = "Firefox",
                emailOrUserName = "firefox@gmail.com",
                passwordItem = "passFromFireFox",
                urlSite = null,
                descriptions = "Its password from browser fireFox",
                changeData = "15.11.2022",
                id = 4,
            ),
        )
    }
}