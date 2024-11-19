package `in`.hypernation.payup.presentation.navigation

sealed class Screen(val route : String) {
    object Home : Screen("home")
    object Payment : Screen("payment/{sender_upiId}/{sender_name}") {
        fun createRoute(upiId: String, name: String) = "payment/${upiId}/${name}"
    }
    object Result : Screen("result/{status}") {
        fun createRoute(status : String) = "result/${status}"
    }
}