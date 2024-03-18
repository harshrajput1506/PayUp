package `in`.hypernation.payup

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.di.appModule
import `in`.hypernation.payup.presentation.home.HomeView
import `in`.hypernation.payup.ui.theme.PayUpTheme
import `in`.hypernation.payup.utils.BYPASS_LANGUAGE
import `in`.hypernation.payup.utils.USSD_CODE
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        Timber.d("Init Application\nSetting Koin Architecture...")
        val permission = Manifest.permission.READ_PHONE_STATE

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this as Activity, arrayOf(permission), 1001)
        } else {
            // Permission has already been granted
            // Your code to access phone state
        }
        setContent {
            PayUpTheme {
                val context : Context = LocalContext.current
                // A surface container using the 'background' color from the theme
                KoinApplication(application = {
                    // your preview config here

                    androidContext(this@MainActivity)
                    modules(appModule)
                }) {
                    // Compose to preview with Koin
                    HomeView()
                }

            }
        }
    }
}