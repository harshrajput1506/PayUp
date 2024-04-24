package `in`.hypernation.payup.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.hypernation.payup.R
import `in`.hypernation.payup.data.USSD.USSDResponse
import `in`.hypernation.payup.data.local.PreferenceManager
import `in`.hypernation.payup.data.models.Account
import `in`.hypernation.payup.data.repo.HomeRepository
import `in`.hypernation.payup.utils.BALANCE_CODE
import `in`.hypernation.payup.utils.USSD_CODE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val repository: HomeRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _linkState = mutableStateOf(LinkState())
    val linkState = _linkState

    private val _scanState = MutableStateFlow(ScanState())
    val scanState = _scanState.asStateFlow()

    val menuItems = listOf(
        Pair("Scan any QR Code", R.drawable.scan),
        Pair("Pay UPI ID", R.drawable.at_the_rate),
        Pair("Bank Transfer", R.drawable.account_balance)
    )

    init {
        val account = Account(
            bankName = preferenceManager.getBankName(),
            isLinked = preferenceManager.getAccountLinkStatus(),
            bankBalance = null
        )
        _linkState.value = LinkState(account = account)
        Timber.d(preferenceManager.getAccountLinkStatus().toString())
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Linked -> linkUPI()
            HomeEvent.OnPayWithQR -> payWithQR()
            HomeEvent.OnPayWithUPI -> payWithUPI()
            HomeEvent.OnCheckBalance -> checkBalance()
            HomeEvent.OnDismissDialog -> dismissDialog()
        }
    }

    private fun dismissDialog() {val account = Account(
        bankName = preferenceManager.getBankName(),
        isLinked = preferenceManager.getAccountLinkStatus(),
        bankBalance = null
    )
        _linkState.value = LinkState(account = account)
    }

    private fun linkUPI() {
        //ussdApi.cancel()
        viewModelScope.launch {
            //val account : Account = repository.linkAccount(0)
            repository.linkAccount(USSD_CODE,0) { response ->
                when (response) {
                    is USSDResponse.Success -> {
                        _linkState.value = response.data?.let {
                            LinkState(
                                account = it,
                                message = "Successfully Linked",
                                isLoading = false
                            )
                        }!!
                        Timber.d(linkState.toString())
                    }

                    is USSDResponse.Error -> {
                        _linkState.value =
                            LinkState(message = "Not Linked! Something Wrong", isLoading = false)
                    }

                    else -> {
                        _linkState.value = LinkState(isLoading = true)
                    }
                }
                response.data?.isLinked?.let { preferenceManager.setAccountLinkStatus(it) }
                preferenceManager.setBankName(response.data?.bankName)
            }
        }
    }

    private fun checkBalance(){
        viewModelScope.launch {
            repository.checkBalance(BALANCE_CODE,0){ response ->
                when(response){
                    is USSDResponse.Success -> {
                        _linkState.value = LinkState(
                            account = Account(
                                bankBalance = response.data?.bankBalance,
                                isLinked = preferenceManager.getAccountLinkStatus(),
                                bankName = preferenceManager.getBankName()
                            ),
                            message = response.message
                        )
                    }

                    is USSDResponse.Error -> {
                        Timber.d(response.message)
                        _linkState.value = LinkState(
                            account = Account(
                                bankBalance = null,
                                isLinked = preferenceManager.getAccountLinkStatus(),
                                bankName = preferenceManager.getBankName()
                            ),
                            message = response.message
                        )
                        Timber.d(_linkState.toString())
                    }

                    else -> {
                        _linkState.value = LinkState(isLoading = true)
                    }

                }
            }
        }
    }

    private fun payWithQR() {
        viewModelScope.launch {
            repository.scanQr().collect{
                _scanState.value = scanState.value.copy(
                    details = it
                )
                Timber.d("Scan Details : $it")
            }
        }
    }

    private fun payWithUPI() {

    }
}



