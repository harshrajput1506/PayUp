package `in`.hypernation.payup.presentation.navigation

sealed class Screen(val route : String) {
    object Home : Screen("home")
    object Payment : Screen("payment")
}