package `in`.hypernation.payup.data.models

data class Payment(
    val isSuccess : Boolean,
    val refId : String,
    val upiId: String,
    val amount: String,
    val name: String
)
