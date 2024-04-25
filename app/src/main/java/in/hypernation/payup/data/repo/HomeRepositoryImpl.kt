package `in`.hypernation.payup.data.repo

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.manipulation.StringManipulation
import `in`.hypernation.payup.data.models.Account
import `in`.hypernation.payup.data.models.PayCredentials
import `in`.hypernation.payup.utils.BYPASS_LANGUAGE
import `in`.hypernation.payup.utils.OPTION_VIEW
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeRepositoryImpl (
    private val ussdApi : USSDApi,
    private val context: Context,
    private val stringManipulation: StringManipulation,
    private val scanner: GmsBarcodeScanner
) : HomeRepository {
    override suspend fun linkAccount(code: String, simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit) {
        ussdApi.callUSSDRequest(context, code, simSlot, object : USSDBuilder.CallBack{
            override fun response(message: String, isError : Boolean) {
                // First time shows Welcome View
                Timber.d(message)
                if(message.contains(BYPASS_LANGUAGE)){
                    ussdApi.send("1"){}
                } else if(message.contains(OPTION_VIEW)) {
                    ussdApi.cancel()
                    // String Bank name
                    val bankName = stringManipulation.getBankName(message)
                    onResponse(USSDResponse.Success(Account(bankName, null, true)))

                } else {
                    onResponse(USSDResponse.Error(message = "Can't Linked Right Now"))
                }
            }

            override fun over(message: String, isError: Boolean) {
                // Message = Check your accessibility
                // USSD Not Good
                //String -> Balance
                Timber.d(message)
                if(isError){
                    return onResponse(USSDResponse.Error(message =  if(message == "Check your accessibility") "Accessibility Permission" else "Something Went Wrong"))
                }
                val bankBalance = stringManipulation.getBankBalance(message)
                return onResponse(USSDResponse.Success(Account(null, bankBalance = bankBalance, true)))
            }
        })

    }

    override suspend fun checkBalance(code : String, simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit) {
        ussdApi.callUSSDRequest(context, code, simSlot, object : USSDBuilder.CallBack{
            override fun response(message: String, isError : Boolean) {
                Timber.d(message)

                // Error Handling Time out error server error external application down error
                /*if(message.contains(OPTION_VIEW)){

                }*/
            }

            override fun over(message: String, isError : Boolean) {
                Timber.d(message)
                if(isError){
                    return onResponse(USSDResponse.Error(message =  if(message == "Check your accessibility") "Accessibility Permission" else "Can't fetch your balance"))
                }
                val bankBalance = stringManipulation.getBankBalance(message)
                return onResponse(USSDResponse.Success(Account(bankBalance = bankBalance, isLinked = true, bankName = null)))


            }

        })
    }
    override fun scanQr(): Flow<PayCredentials?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {barcode ->
                    launch {
                        barcode.rawValue?.let { send(getCredentials(it)) }
                    }
                }.addOnCanceledListener {
                    launch {
                        send(null)
                    }
                }.addOnFailureListener{
                    launch {
                        send(null)
                    }
                }
            awaitClose {  }
        }
    }

    private fun getCredentials(value : String) : PayCredentials? {
        val (pmo, pa, pn) = stringManipulation.extractUPIValues(value)
        pa?.let {
            return PayCredentials(
                upiId = pa,
                name = pn ?: "",
                number = pmo ?: ""
            )
        }

        return null;
    }
}