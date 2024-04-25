package `in`.hypernation.payup.presentation.home

import `in`.hypernation.payup.data.models.PayCredentials

data class ScanState(
    val credentials: PayCredentials? = null,
    val isUPIPayment : Boolean = false,
    val message: String = ""
)
