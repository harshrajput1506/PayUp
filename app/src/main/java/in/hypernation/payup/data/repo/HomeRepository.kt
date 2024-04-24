package `in`.hypernation.payup.data.repo

import android.content.Context
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.models.Account
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun linkAccount(code : String, simSlot : Int, onResponse : (USSDResponse<Account>) -> Unit)
    fun checkBalance(code : String, simSlot: Int, onResponse: (USSDResponse<Account>) -> Unit)
    fun scanQr() : Flow<String>
}