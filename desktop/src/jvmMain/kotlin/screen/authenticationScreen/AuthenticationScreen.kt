package screen.authenticationScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.pass.word.session.navigation.screen.main.authentication.ScreenAuthenticationComponent
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AuthenticationScreen(component: ScreenAuthenticationComponent) {


    Text("AuthScreen")

}