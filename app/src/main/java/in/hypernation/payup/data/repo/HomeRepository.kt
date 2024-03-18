package `in`.hypernation.payup.data.repo

import android.content.Context
import `in`.hypernation.payup.data.models.Account

interface HomeRepository {

    fun linkAccount(simSlot : Int) : Account
}