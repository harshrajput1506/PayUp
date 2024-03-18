package `in`.hypernation.payup.data.repo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.models.Account
import `in`.hypernation.payup.data.repo.HomeRepository
import `in`.hypernation.payup.utils.BYPASS_LANGUAGE
import `in`.hypernation.payup.utils.USSD_CODE
import timber.log.Timber

class HomeRepositoryImpl (private val ussdApi : USSDApi, private val context: Context) : HomeRepository {
    override fun linkAccount(simSlot: Int): Account {
        val permission = Manifest.permission.READ_PHONE_STATE
        if(ussdApi.verifyAccessibilityAccess(context)){
            ussdApi.callUSSDRequest(context, USSD_CODE, simSlot, object : USSDBuilder.CallBack{
                override fun response(message: String) {
                    // Welcome View
                    Timber.d(message)
                    if(message.contains(BYPASS_LANGUAGE)){
                        ussdApi.send("1"){
                            ussdApi.setDefault(true)
                        }
                    } else {
                        ussdApi.cancel()
                        Timber.d(message)
                        // String Bank name
                    }
                }

                override fun over(message: String) {
                    // Message = Check your accessibility
                    // USSD Not Good

                    //String -> Balance
                    Timber.d(message)

                }

            })
        }


        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 1001)
        } else {
            // Permission has already been granted
            // Your code to access phone state
        }

        return Account("Kotak", "656", true);
    }
}