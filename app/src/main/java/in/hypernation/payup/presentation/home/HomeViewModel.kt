package `in`.hypernation.payup.presentation.home

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.models.Account
import `in`.hypernation.payup.data.repo.HomeRepository
import `in`.hypernation.payup.data.repo.HomeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: HomeRepository
) : ViewModel() {

    private val _linkState = MutableStateFlow(LinkState())
    val linkState = _linkState.asStateFlow()

    fun handleEvent(event: HomeEvent){
        when (event) {
            is HomeEvent.Linked -> linkUPI()
            HomeEvent.OnPayWithQR -> payWithQR()
            HomeEvent.OnPayWithUPI -> payWithUPI()
        }

    }

    private fun linkUPI(){
        //ussdApi.cancel()
        viewModelScope.launch {
            val account : Account = repository.linkAccount(0)
        }
    }

    private fun payWithQR(){

    }

    private fun payWithUPI(){

    }

}