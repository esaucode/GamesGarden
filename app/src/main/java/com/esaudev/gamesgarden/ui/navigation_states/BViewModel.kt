package com.esaudev.gamesgarden.ui.navigation_states

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _argument = MutableLiveData("")
    val argument: LiveData<String>
        get() = _argument

    init {
        val argument = savedStateHandle.get<String>("fragmentB")
        Log.d("TEST_ESAU", "Viewmodel init triggered")
        startService(argument.orEmpty())
    }

    private fun startService(argument: String) {
        viewModelScope.launch {
            Log.d("TEST_ESAU", "Start service triggered")
            _argument.value = argument
        }
    }

}