package com.iliesnz.talkie_walkie_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IHomeService
import com.iliesnz.talkie_walkie_kotlin.viewmodel.state.HomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException

class HomeViewmodel(private val service: IHomeService): ViewModel() {

    private var uiState = MutableStateFlow<HomeUiState>(HomeUiState.Base)
    var uiStateReadOnly: StateFlow<HomeUiState> = uiState.asStateFlow()

    fun connectToServer(ipAddress: String) {
        viewModelScope.launch {
            uiState.value = HomeUiState.Loading
            try {
                service.connectToServer(ipAddress)
                uiState.value = HomeUiState.Success()
            } catch (e: Exception) {
                e.printStackTrace()
                uiState.value = HomeUiState.Error()
            }
        }
    }
}