package com.iliesnz.talkie_walkie_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliesnz.shared.model.Packet
import com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow.PacketHandler
import kotlinx.coroutines.launch


class TalkieViewModel(private val packetHandler: PacketHandler) : ViewModel() {



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