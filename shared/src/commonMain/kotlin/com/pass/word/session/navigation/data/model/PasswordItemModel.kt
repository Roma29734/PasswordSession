package com.pass.word.session.navigation.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PasswordItemModel(
    val nameItemPassword: String,
    val emailOrUserName: String,
    val passwordItem: String,
    val changeData: String,
    val urlSite: String?,
    val descriptions: String?
)