package `in`.hypernation.payup.USSD

interface USSDInterface {
    fun sendData(text: String)
    fun isRunning()
}