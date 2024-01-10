package com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.utilits.onCheckValidation

class ScreenAddPasswordComponent constructor(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val listenersPassCreate = mutableListOf<(message: String, complete: Boolean) -> Unit>()

    fun subscribeListenerPassCreate(listener: (message: String, complete: Boolean) -> Unit) {
        listenersPassCreate.add(listener)
    }

    fun unsubscribeListenerPassCreate(listener: (message: String, complete: Boolean) -> Unit) {
        listenersPassCreate.remove(listener)
    }

    private fun pluckPassCreate(message: String, complete: Boolean) {
        listenersPassCreate.forEach { it.invoke(message, complete) }
    }

    private fun addPassToDataBass(
        title: String,
        email: String,
        pass: String,
        url: String?,
        descriptions: String?,
        databaseDriverFactory: DriverFactory
    ) {
        val model = PasswordItemModel(
            nameItemPassword = title,
            emailOrUserName = email,
            passwordItem = pass,
            changeData = "10.01.2024",
            urlSite = url.onCheckValidation(),
            descriptions = descriptions.onCheckValidation()
        )
        val database = PersonalDatabase(databaseDriverFactory)
        database.createPass(listOf(model))
        pluckPassCreate("Password a success created", true)
    }

    fun onEvent(eventAdd: ScreenAddPasswordStateEvent) {
        when (eventAdd) {
            is ScreenAddPasswordStateEvent.ClickButtonAddNewState -> {
                if (eventAdd.title.isEmpty() || eventAdd.email.isEmpty() || eventAdd.pass.isEmpty()) {
                    pluckPassCreate("Not all fields are filled in", false)
                } else {
                    addPassToDataBass(
                        eventAdd.title,
                        eventAdd.email,
                        eventAdd.pass,
                        eventAdd.url,
                        eventAdd.descriptions,
                        eventAdd.databaseDriverFactory
                    )
                }
            }
        }
    }
}