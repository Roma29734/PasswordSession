package com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel

class ScreenAddPasswordComponent constructor(
    componentContext: ComponentContext
) : ComponentContext by componentContext {


    private val listenersPassCreate = mutableListOf<(message: String) -> Unit>()

    fun subscribeListenerPassCreate(listener: (message: String) -> Unit) {
        listenersPassCreate.add(listener)
    }

    fun unsubscribeListenerPassCreate(listener: (message: String) -> Unit) {
        listenersPassCreate.remove(listener)
    }

    private fun pluckPassCreate(message: String) {
        listenersPassCreate.forEach { it.invoke(message) }
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
            changeData = "10.01.2024",
            passwordItem = pass,
            urlSite = url,
            descriptions = descriptions
        )
        val database = PersonalDatabase(databaseDriverFactory)
        database.createPass(listOf(model))
        pluckPassCreate("Password a success created")
    }

    fun onEvent(eventAdd: ScreenAddPasswordStateEvent) {
        when (eventAdd) {
            is ScreenAddPasswordStateEvent.ClickButtonAddNewState -> {
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