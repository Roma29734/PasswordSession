package com.pass.word.session.utilits

import androidx.compose.runtime.Composable
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.data.model.PasswordListContainer
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
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

fun getThisLocalTime(): String {
    return try {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dayOfMonth =  currentDateTime.dayOfMonth.toDateSimpleFormat()
        val monthNumber = currentDateTime.monthNumber.toDateSimpleFormat()
        val year = currentDateTime.year
        val date = "${dayOfMonth}.${monthNumber}.${year}"
        println("this local date - $date")
        date
    } catch (e: Exception) {
        println("this local date error - ${e.message}")
        ""
    }
}

fun Int.toDateSimpleFormat(): String {
    if(this >= 10) return this.toString()
    return "0$this"
}


expect fun vibrationResponse(time: Int, context: Any)


expect fun checkUseBiometric(
    context: Any,
    onAction: (successState: Boolean, message: String?) -> Unit,
)

@Serializable
data class Data(val items: List<String>)

fun listToStringJson(list: List<String>): String {
    val data = Data(list)
    return Json.encodeToString(Data.serializer(), data)
}

fun jsonStringToList(jsonString: String): List<String> {
    val data = Json.decodeFromString(Data.serializer(), jsonString)
    return data.items
}



class EventDispatcher<T> {
    private val listeners = mutableListOf<(T) -> Unit>()

    // Метод для подписки на событие
    fun subscribe(listener: (T) -> Unit): () -> Unit {
        listeners.add(listener)
        return {unsubscribe (listener)}
    }

    // Метод для отправки события всем подписчикам
    fun dispatch(event: T) {
        listeners.forEach { it(event) }
    }

    fun unsubscribe(listener: (T) -> Unit) {
        listeners.remove(listener)
    }
}


sealed class ResponseStatus() {
    data object Success: ResponseStatus()
    data class Error (val errorMessage: String): ResponseStatus()
}
