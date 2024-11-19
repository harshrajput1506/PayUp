package `in`.hypernation.payup.presentation.payment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.models.Account
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

    private val _state = mutableStateOf(PaymentState())
    val state = _state

    fun dismissDialog() {
        _state.value = PaymentState()
    }

    fun onPay(amount: String, remarks: String){
        viewModelScope.launch {
            val remarksInput = remarks.ifEmpty { "1" }
            paymentRepository.payByQR(QR_PAYMENT_CODE, 0, credentials.upiId, amount, remarksInput){
                when (it){
                    is USSDResponse.Success -> {
                        _state.value = PaymentState(isSuccess = true, status = if(it.data?.isSuccess != false) "success" else "failed", message = it.data?.refId)
                    }
                    is USSDResponse.Error -> {
                        _state.value = PaymentState(status = "failed", isSuccess = false, message = it.message)
                    }
                }

            }
        }
    }



}