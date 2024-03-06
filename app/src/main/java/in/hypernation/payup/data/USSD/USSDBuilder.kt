package `in`.hypernation.payup.data.USSD

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.telecom.TelecomManager
import android.view.accessibility.AccessibilityManager
import `in`.hypernation.payup.data.USSD.USSDApi


@SuppressLint("StaticFieldLeak")
object USSDBuilder : USSDApi, USSDInterface {

    private val simSlotName = arrayOf("extra_asus_dial_use_dualsim",
        "com.android.phone.extra.slot", "slot", "simslot", "sim_slot", "subscription",
        "Subscription", "phone", "com.android.phone.DialingMode", "simSlot", "slot_id",
        "simId", "simnum", "phone_type", "slotId", "slotIdx")

    val map : HashMap<String, List<String>> = hashMapOf(
        "BYPASS" to listOf("select Language", "Language", "Select Option"),
        "LOGIN" to listOf("WELCOME", "bank's name", "UPI PIN"),
        "ERROR" to listOf("timed out", "Timed", "timed", "Timed out")
    )

    lateinit var context : Context
        private set

    lateinit var callBack : CallBack

    var isRunning : Boolean? = false
        private set

    var sendType : Boolean? = false
        private set

    var callBackMessage:((String)->Unit)? = null
        private set

    private var ussdInterface : USSDInterface? = null;

    init {
        ussdInterface = this
    }



    override fun send(text: String, callBackMessage: (String) -> Unit) {
        this.callBackMessage = callBackMessage
        sendType = true
        ussdInterface?.sendData(text)
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    override fun callUSSDRequest(context: Context, code: String, simSlot: Int, callBack: CallBack) {
        sendType = false
        this.context = context
        this.callBack = callBack
        if (verifyAccessibilityAccess(context)) {
            dialUp(code, simSlot)
        } else {
            callBack.over("Check your accessibility")
        }

    }

    override fun verifyAccessibilityAccess(context: Context): Boolean {
        val isAccessibilityEnabled = isAccessibilityServicesEnable(context)
        if(!isAccessibilityEnabled) openSettingsAccessibility(context as Activity)
        return isAccessibilityEnabled

    }

    override fun sendData(text: String) {
        TODO("Not yet implemented")
    }

    override fun stopRunning() {
        isRunning = false
    }

    interface CallBack{
        fun response(message: String)
        fun over(message: String)
    }

    private fun dialUp(ussdPhoneNumber: String, simSlot: Int) {
        when {
            !map.containsKey("LOGIN") || !map.containsKey("ERROR") || !map.containsKey("BYPASS") ->
                callBack.over("Bad Mapping structure")
            ussdPhoneNumber.isEmpty() -> callBack.over("Bad ussd number")
            else -> {
                var phone = Uri.encode("#")?.let {
                    ussdPhoneNumber.replace("#", it)
                }
                isRunning = true
                context.startActivity(getActionCallIntent(Uri.parse("tel:$phone"), simSlot))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getActionCallIntent(uri: Uri?, simSlot: Int): Intent {
        val telcomManager = context.getSystemService(Context.TELECOM_SERVICE) as? TelecomManager
        return Intent(Intent.ACTION_CALL, uri).apply {
            simSlotName.map { sim -> putExtra(sim, simSlot) }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("com.android.phone.force.slot", true)
            putExtra("Cdma_Supp", true)
            telcomManager?.callCapablePhoneAccounts?.let { handles ->
                if (handles.size > simSlot)
                    putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", handles[simSlot])
            }
        }
    }

    private fun isAccessibilityServicesEnable(context: Context): Boolean {
        (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager)?.apply {
            installedAccessibilityServiceList.forEach { service ->
                if (service.id.contains(context.packageName) &&
                    Settings.Secure.getInt(context.applicationContext.contentResolver,
                        Settings.Secure.ACCESSIBILITY_ENABLED) == 1)
                    Settings.Secure.getString(context.applicationContext.contentResolver,
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)?.let {
                        if (it.split(':').contains(service.id)) return true
                    }
            }
        }
        return false
    }

    private fun openSettingsAccessibility(activity: Activity) =
        with(AlertDialog.Builder(activity)) {
            setTitle("USSD Accessibility permission")
            setMessage("You must enable accessibility permissions for the app %s".format(getNameApp(activity)))
            setCancelable(true)
            setNeutralButton("ok") { _, _ ->
                activity.startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
            }
            create().show()
        }

    private fun getNameApp(activity: Activity): String = when (activity.applicationInfo.labelRes) {
        0 -> activity.applicationInfo.nonLocalizedLabel.toString()
        else -> activity.getString(activity.applicationInfo.labelRes)
    }
}