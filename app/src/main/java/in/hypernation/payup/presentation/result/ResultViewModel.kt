package `in`.hypernation.payup.presentation.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ResultViewModel (
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val status : String = savedStateHandle["status"] ?: ""

}