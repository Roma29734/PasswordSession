package com.pass.word.session.utilits

import androidx.compose.runtime.Composable
import com.pass.word.session.data.model.PasswordItemModel
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Composable
expect fun showToast(message: String)

fun String?.onCheckValidation (): String? {
    if(this == null) return null
    return this.ifEmpty { null }
}

expect fun createAndSaveJsonFile(context: Any, fileName: String, savedJson: JsonObject)


fun convertListToJsonObject(passwordList: List<PasswordItemModel>): JsonObject {
    return buildJsonObject {
        put("passwordList", buildJsonArray {
            passwordList.forEach { passwordItem ->
                add(buildJsonObject {
                    put("id", passwordItem.id)
                    put("nameItemPassword", passwordItem.nameItemPassword)
                    put("emailOrUserName", passwordItem.emailOrUserName)
                    put("passwordItem", passwordItem.passwordItem)
                    put("changeData", passwordItem.changeData)
                    put("urlSite", passwordItem.urlSite)
                    put("descriptions", passwordItem.descriptions)
                })
            }
        })
    }
}





