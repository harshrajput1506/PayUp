package `in`.hypernation.payup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PayCredentials(
    val upiId : String,
    val name: String,
    val number: String
) : Parcelable
