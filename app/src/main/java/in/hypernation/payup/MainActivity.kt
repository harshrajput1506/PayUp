package `in`.hypernation.payup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import `in`.hypernation.payup.presentation.home.HomeView
import `in`.hypernation.payup.ui.theme.PayUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayUpTheme {
                // A surface container using the 'background' color from the theme
                HomeView()
            }
        }
    }
}