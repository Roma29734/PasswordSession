package screen.initialGreeting.tonInitRoot.enterSeedPhrase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.EnterSeedPhraseScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.ScreenEnterSeedPhraseComponent

@Composable
fun EnterSeedPhraseScreen(component: ScreenEnterSeedPhraseComponent) {

    val passEnterState by component.stateEnableButtonNext.collectAsState()
    val stateSeedText by component.stateSeedText.collectAsState().value
    val stateOpenDialogLoading by component.stateOpenDialogLoading.collectAsState()
    val stateLoadingAlert by component.stateLoadingAlert.collectAsState()


    EnterSeedPhraseScreenContent(
        passEnterState = passEnterState,
        stateSeedText = stateSeedText,
        stateOpenDialogLoading = stateOpenDialogLoading,
        stateLoadingAlert = stateLoadingAlert
    ) { component.onEvent(it) }

}