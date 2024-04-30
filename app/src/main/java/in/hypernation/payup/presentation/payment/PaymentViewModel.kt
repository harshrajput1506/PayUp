package `in`.hypernation.payup.presentation.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.models.PayCredentials
import `in`.hypernation.payup.data.models.PaymentResponse
import `in`.hypernation.payup.data.repo.PaymentRepository
import `in`.hypernation.payup.utils.QR_PAYMENT_CODE
import kotlinx.coroutines.launch


class PaymentViewModel (
    savedStateHandle: SavedStateHandle,
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    val credentials = PayCredentials(
        upiId = savedStateHandle["sender_upiId"] ?: "",
        name = savedStateHandle["sender_name"] ?: "",
        number = ""
    )

    fun onPay(amount: String, remarks: String){
        viewModelScope.launch {
            val remarksInput = remarks.ifEmpty { "1" }
            paymentRepository.payByQR(QR_PAYMENT_CODE, 0, credentials.upiId, amount, remarksInput){
                when (it){
                    is USSDResponse.Success -> {

                    }
                    is USSDResponse.Error -> {

                    }
                }

            }
        }
    }



}