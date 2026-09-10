package com.iliesnz.talkie_walkie_kotlin.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.ISessionService
import com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewmodel(private val service: ISessionService): ViewModel() {


    private var uiState = MutableStateFlow<HomeUiState>(HomeUiState.Base)
    var uiStateReadOnly: StateFlow<HomeUiState> = uiState.asStateFlow()

    fun connectToTCP(ipAddress: String) {

        if (ipAddress.isEmpty()) {
            uiState.value = HomeUiState.Error("Il manque l'ip du serveur !")
            return
        }

        viewModelScope.launch {
            uiState.value = HomeUiState.Loading
            try {
                service.connectToTCP(ipAddress)
                uiState.value = HomeUiState.Success()
            } catch (e: Exception) {
                e.printStackTrace()
                uiState.value = HomeUiState.Error("Serveur introuvable")
            }
        }

    }

    fun resetState() {
        uiState.value = HomeUiState.Base
    }


}