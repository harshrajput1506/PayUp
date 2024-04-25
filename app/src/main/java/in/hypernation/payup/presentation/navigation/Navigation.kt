package `in`.hypernation.payup.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import `in`.hypernation.payup.presentation.home.HomeScreen
import `in`.hypernation.payup.presentation.payment.PaymentScreen
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import kotlin.math.log



@Composable
fun Navigation(
    openSettings : () -> Unit
){
    val controller = rememberNavController()
    controller.enableOnBackPressed(true)
    NavHost(navController = controller, startDestination = Screen.Home.route) {

        composable(route = Screen.Home.route){
            HomeScreen(
                openSettings = { openSettings() },
                goToPayment = { controller.navigate(Screen.Payment.route) }
            )
        }

        composable(route = Screen.Payment.route) {
            PaymentScreen(
                backToHome = {
                    controller.navigate(Screen.Home.route) {
                        popUpTo(controller.graph.id){
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}