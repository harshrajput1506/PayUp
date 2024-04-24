package `in`.hypernation.payup

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.di.appModule
import `in`.hypernation.payup.presentation.home.HomeView
import `in`.hypernation.payup.presentation.permissions.PermissionView
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

        val requestPermission = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE
        )

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
                    HomeView(){
                        openSettings(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    }
                    PermissionView(requestPermissions = requestPermission, isPermanentlyDeclined = {perm ->
                         !shouldShowRequestPermissionRationale(perm)
                    }) {
                        openSettings(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    }
                }

            }
        }
    }
}

fun Activity.openSettings(setting : String){
    Intent(
        setting  //Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    ).also ( ::startActivity )
}
