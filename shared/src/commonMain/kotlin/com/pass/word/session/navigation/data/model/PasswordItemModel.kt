package com.pass.word.session.navigation.data.model

data class PasswordItemModel(
    val nameItemPassword: String,
    val emailOrUserName: String,
    val passwordItem: String,
    val changeData: String,
    val urlSite: String?,
    val descriptions: String?
)