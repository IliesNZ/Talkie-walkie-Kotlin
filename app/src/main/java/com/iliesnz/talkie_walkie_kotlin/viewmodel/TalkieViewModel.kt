package com.iliesnz.talkie_walkie_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliesnz.shared.model.Packet
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IAudioService
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.ISessionService
import com.iliesnz.talkie_walkie_kotlin.service.sharedFlow.AudioHandler
import com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow.PacketHandler
import com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow.TalkieUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class TalkieViewModel(private val sessionService: ISessionService, private val audioService: IAudioService, private val packetHandler: PacketHandler, private val audioHandler: AudioHandler) : ViewModel() {

    private val uiState = MutableStateFlow<TalkieUiState>(TalkieUiState.base)
    val uiStateReadOnly: StateFlow<TalkieUiState> = uiState.asStateFlow()

    fun listeningUDP() {
        viewModelScope.launch {
            audioService.listenUDP()    // Receptione le sons en UDP
        }
        viewModelScope.launch {
            listeningAudio()
        }
    }

    private suspend fun listeningAudio(){
        audioHandler.audioInReadOnly.collect { audioData ->
            uiState.value = TalkieUiState.incomingSound
            audioService.listenAudio(audioData)
        }
    }

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
                uiState.value = TalkieUiState.comingOutSound
                audioService.startCommunication()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun stopCommunication(){
        uiState.value = TalkieUiState.base
        audioService.stopCommunication()
    }

    fun listeningTCP(){
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