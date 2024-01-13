package com.pass.word.session.navigation.screen.bottom.screenSettingsComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.utilits.createAndSaveJsonFile

class ScreenSettingsComponent constructor(
    private val componentContext: ComponentContext
) : ComponentContext by componentContext {


    fun clickToButtonDownloadPass(context: Any) {
        createAndSaveJsonFile(context, "examplePass.json")
    }

}