package `in`.hypernation.payup.data.repo

import android.content.Context
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.models.Account
import `in`.hypernation.payup.data.models.PayCredentials
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun linkAccount(code : String, simSlot : Int, onResponse : (USSDResponse<Account>) -> Unit)
    suspend fun checkBalance(code : String, simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit)
    fun scanQr() : Flow<PayCredentials?>
}