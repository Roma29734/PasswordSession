package com.pass.word.session.navigation.screen.main.detail

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.navigation.data.model.PasswordItemModel
import com.pass.word.session.utilits.showToast

class ScreenDetailComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
): ComponentContext by componentContext {



    fun onEvent(event: ScreenDetailEvent) {
        when(event) {
            is ScreenDetailEvent.ClickButtonBack -> { onGoBack() }
            is ScreenDetailEvent.ShowToast -> {

            }
        }
    }

}