package com.pass.word.session.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PasswordItemModel(
    val id: Int,
    val nameItemPassword: String,
    val emailOrUserName: String,
    val passwordItem: String,
    val changeData: String,
    val urlSite: String?,
    val descriptions: String?
)