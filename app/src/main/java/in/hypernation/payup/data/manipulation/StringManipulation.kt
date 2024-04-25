package `in`.hypernation.payup.data.manipulation

import `in`.hypernation.payup.utils.RUPEE_SYMBOL

class StringManipulation {

    fun getBankName(message: String): String? {
        val text = message.trimIndent()
        val bankNameRegex = Regex("Select Option: (.+)")
        val matchResult = bankNameRegex.find(text)
        return matchResult?.groups?.get(1)?.value?.trim()
    }

    fun getBankBalance(message: String): String? {
        val balanceRegex = Regex("Rs\\.([0-9,.]+)")
        val matchResult = balanceRegex.find(message)
        val balanceString = matchResult?.groups?.get(1)?.value
        val bankBalance = balanceString?.replace(",", "")?.toDoubleOrNull()
        var balanceS : String? = null
        if(bankBalance != null){
            balanceS = "$RUPEE_SYMBOL $bankBalance"
        }
        return balanceS
    }

    fun extractUPIValues(upiString: String): Triple<String?, String?, String?> {
        // Regular expression pattern to match UPI parameters
        val regex = """upi://pay\?(.*)""".toRegex()
        val matchResult = regex.find(upiString)

        var pmo: String? = null
        var pa: String? = null
        var pn: String? = null

        matchResult?.let {
            val parameters = it.groups[1]?.value
            parameters?.split("&")?.forEach { param ->
                val keyValue = param.split("=")
                if (keyValue.size == 2) {
                    when (keyValue[0]) {
                        "pmo" -> pmo = keyValue[1]
                        "pa" -> pa = keyValue[1]
                        "pn" -> pn = keyValue[1].replace("%20", " ")
                    }
                }
            }
        }

        return Triple(pmo, pa, pn)
    }
}