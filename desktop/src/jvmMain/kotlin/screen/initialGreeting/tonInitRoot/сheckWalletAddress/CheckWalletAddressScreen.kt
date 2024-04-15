package screen.initialGreeting.tonInitRoot.сheckWalletAddress

import androidx.compose.runtime.Composable
import com.pass.word.session.navigation.screen.initialGreeting.screenCheckWalletAddress.CheckWalletAddressScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenCheckWalletAddress.ScreenCheckWalletAddressComponent

@Composable
fun CheckWalletAddressScreen(component: ScreenCheckWalletAddressComponent) {
    CheckWalletAddressScreenContent(
        handlerBackNav = {
            component.onBack()
        },
        handlerNextNav = {
            component.onNext()
        },
        txAddress = component.itemWalletAddress
    )
}