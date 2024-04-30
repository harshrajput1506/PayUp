package `in`.hypernation.payup.data.repo

import android.content.Context
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.manipulation.StringManipulation
import `in`.hypernation.payup.data.models.PaymentResponse
import timber.log.Timber

class PaymentRepositoryImpl (
    private val context: Context,
    private val ussdApi: USSDApi,
    private val stringManipulation: StringManipulation
) : PaymentRepository {

    override suspend fun payByQR(code: String, simSlot: Int, upiId: String, amount: String, remarks: String ,onResponse: (USSDResponse<PaymentResponse>) -> Unit) {
        var refId: String? = null
        ussdApi.callUSSDRequest(context, code, simSlot, object : USSDBuilder.CallBack{
            override fun response(message: String, isError: Boolean) {

                if(stringManipulation.checkUpiIdBox(message)){
                    // Collect refId (Payment Successful)
                    ussdApi.send(upiId){ amountMsg ->
                        if (stringManipulation.checkAmountBox(amountMsg)){
                            ussdApi.send(amount){ remarksMsg ->
                                if (stringManipulation.checkRemarkBox(remarksMsg)) {
                                    ussdApi.send(remarks){
                                        if(stringManipulation.checkPaymentSuccess(it)){
                                            // Collect refId (Payment Successful)
                                            refId = stringManipulation.extractRefId(it)
                                            ussdApi.send("2"){ }
                                        }
                                        Timber.d("Inner Msg : $it")
                                    }
                                }
                            }
                        }
                    }
                }

                
            }

            override fun over(message: String, isError: Boolean) {
                Timber.d("Over Msg - $message")
                Timber.d("Over RefID - $refId")
                if(message == "Check your accessibility"){
                    return onResponse(USSDResponse.Error(
                        message = "Accessibility Permission"
                        ))
                }

                if(stringManipulation.checkThankYouMsg(message)){
                    // Success
                    //callback success (Payment Successful)
                    refId?.let {
                        //callback success (Payment Successful)
                        return onResponse(USSDResponse.Success(
                            PaymentResponse(
                            isSuccess = true,
                            refId = it
                        )
                        ))

                    }
                    //callback error (Payment Failed)
                }
                if (stringManipulation.checkPaymentFailed(message)){
                    // collect ref id (Payment Failed)
                    refId = stringManipulation.extractRefId(message)
                    // callback success (Payment Failed)
                    refId?.let {
                        //callback success (Payment Successful)
                        return onResponse(USSDResponse.Success(
                            PaymentResponse(
                                isSuccess = false,
                                refId = it
                            )
                        ))

                    }
                }
            }

        })
    }
}