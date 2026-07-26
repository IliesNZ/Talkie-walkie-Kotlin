package com.iliesnz.talkie_walkie_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliesnz.shared.model.Packet
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.ISessionService
import com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow.PacketHandler
import com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow.TalkieUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class TalkieViewModel(private val sessionService: ISessionService, private val packetHandler: PacketHandler) : ViewModel() {

    private val uiState = MutableStateFlow<TalkieUiState>(TalkieUiState.base)
    val uiStateReadOnly: StateFlow<TalkieUiState> = uiState.asStateFlow()

    fun disconnectToTCP(){
        viewModelScope.launch {
            sessionService.disconnectToTCP()
        }
    }

    fun changeChannel(channel: Int){
        viewModelScope.launch {
            try {
                sessionService.changeChannel(channel)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun startCommunication(){
        viewModelScope.launch {
            try {
                sessionService.startCommunication()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun stopCommunication(){
        sessionService.stopCommunication()
    }

    fun listening(){
        viewModelScope.launch {
            packetHandler.packetInReadOnly.collect {
                packet -> packetManager(packet)
            }
        }
    }

    private fun packetManager(packet: Packet){
        when(packet.getType()){
            "RETURN_SESSION" -> {
                val data = (packet.getData() as Number).toInt()

                sessionService.changeSessionCode(data)
                println("Code de la session : " + data)
            }

            "OK" -> {

            }

            "INVALID_REQUEST" -> {

            }

            else -> {

            }
        }
    }

}