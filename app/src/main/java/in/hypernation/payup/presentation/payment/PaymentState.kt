package `in`.hypernation.payup.presentation.payment

data class PaymentState(
   val isSuccess: Boolean = false,
   val status : String = "",
   val message : String? = null,
)
