package `in`.hypernation.payup.data.repo

import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.models.PaymentResponse
import java.net.CacheResponse

interface PaymentRepository {
    suspend fun payByQR(code: String, simSlot: Int, upiId: String, amount: String, remarks: String ,onResponse: (USSDResponse<PaymentResponse>) -> Unit)
}