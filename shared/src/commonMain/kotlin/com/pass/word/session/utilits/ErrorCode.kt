package com.pass.word.session.utilits

// Здесь будут стейты кода, к примеру код 01 будет означать что у пользователя отсутствует интернет

// Код начинается с CD - дальше идет сам № кода
// Коды начинающиеся с 0,1,2 - Означают внутренюю проблему, программную ошибку, или аппаратную
// Коды начинающиеся с 3 - Означают проблему в бд
// Коды начинабщиеся с 4 - Означают серверную проблемму в данном случае блокчейн
enum class ResponseCodState {
    CD00, // unknown error
    CD01, // Lack of Internet
    CD02, // The request time has expired
    CD401, // Lite Server error
    CD402, // Error Seed phrase
    CD403, // Error Key phrase
    CD404, // Error smart contract not deployed
    CD405, // Error child smart contract address empty
}


fun ResponseCodState.convertToMessageAndCode() : String {
    return "$this ${this.conversionToMessage()}"
}

// Функция для конвертации кода в текст ошибки
fun ResponseCodState.conversionToMessage(): String {
    return when (this) {

        ResponseCodState.CD00 -> {
            "An unknown error has occurred"
        }

        ResponseCodState.CD01 -> {
            "Internet connection lost"
        }

        ResponseCodState.CD02 -> {
            "The request time is excessive"
        }

        ResponseCodState.CD401 -> {
            ""
        }

        ResponseCodState.CD402 -> {
            ""
        }

        ResponseCodState.CD403 -> {
            ""
        }

        ResponseCodState.CD404 -> {
            ""
        }

        ResponseCodState.CD405 -> {
            ""
        }
    }
}