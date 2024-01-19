package com.pass.word.session.data.model

import kotlinx.serialization.Serializable


@Serializable
data class PasswordListContainer(
    val passwordList: List<PasswordItemModel>
)