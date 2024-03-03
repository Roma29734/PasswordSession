package com.pass.word.session.navigation.screen.main.initialGreeting.screenCheckWalletAddress

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.putToParams

class ScreenCheckWalletAddressComponent(
    componentContext: ComponentContext,
    private val navToNextScreen: () -> Unit,
    private val navToBackScreen: () -> Unit,
    private val walletAddress: String,
    private val jsonSeedPhrase: String
) {

    val itemWalletAddress = walletAddress

    fun onNext() {
        jsonSeedPhrase.putToParams(keyWalletSeed)
        navToNextScreen()
    }

    fun onBack() {
        navToBackScreen()
    }

}