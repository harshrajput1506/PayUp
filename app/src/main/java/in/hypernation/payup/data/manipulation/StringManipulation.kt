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

    fun extractRefId(input: String): String? {
        val regex = Regex("RefId: (\\d+)")
        val matchResult = regex.find(input)
        return matchResult?.groups?.get(1)?.value
    }

    fun checkPaymentSuccess(message: String) : Boolean {
        val tags = listOf("payment", "successful", "RefId")
        if(message.contains(tags[0]) && message.contains(tags[1]) && message.contains(tags[2])){
            return true
        }
        return false
    }

    fun checkPaymentFailed(message: String) : Boolean {
        val tags = listOf("payment", "failed", "RefId")
        return message.contains(tags[0]) && message.contains(tags[1]) && message.contains(tags[2])
    }

    fun checkThankYouMsg(message: String) : Boolean{
        val tag = "Thank you for using our services"
        return message.contains(tag)
    }

    fun checkUpiIdBox(message: String) : Boolean{
        val tag = "Enter UPI ID"
        return message.contains(tag)
    }

    fun checkAmountBox(message: String) : Boolean{
        val tag = "Enter Amount"
        return message.contains(tag)
    }

    fun checkRemarkBox(message: String) : Boolean{
        val tag = "Enter a remark"
        return message.contains(tag)
    }

    fun extractMessage(input: String): String? {
        val index = input.indexOf(" OK")
        return if (index != -1) {
            input.substringBefore(" OK")
        } else {
            null
        }
    }
}