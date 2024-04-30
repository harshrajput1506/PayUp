package `in`.hypernation.payup

import `in`.hypernation.payup.data.manipulation.StringManipulation
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testStringManipulationBankBalance(){
        val text = "[Your account balance is Rs.7077000.99, OK]"
        val stringManipulation = StringManipulation()
        val bankBalance = stringManipulation.getBankBalance(text)
        println(bankBalance)

    }

    @Test
    fun testExtractUpiValues(){
        val upiString = "559385upi://"  //upi://pay?pmo=9717444267&pn=ShiftingWale.Com&pa=9717444267@ybl&cu=INR&mc=0000
        val (pmo, pa, pn) = StringManipulation().extractUPIValues(upiString)
        println("PMO: $pmo, PA: $pa, PN: $pn")
    }
    @Test
    fun testExtractRefId(){
        val input = "[Your payment to Satyam Kumar Jha failed (RefId: 412111884547) ., OK]"
        val refId = StringManipulation().extractRefId(input)
        println("Extracted RefId: $refId")
    }

    @Test
    fun testExtractMessage(){
        val input = "Your UPI PIN length is incorrect, OK"
        val message = StringManipulation().extractMessage(input)
        println("Extracted message: $message")
    }
}