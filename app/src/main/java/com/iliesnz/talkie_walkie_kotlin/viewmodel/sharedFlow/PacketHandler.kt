package com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow

import com.iliesnz.shared.model.Packet
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PacketHandler() {

    private val packetIn = MutableSharedFlow<Packet>(replay = 1, extraBufferCapacity = 10)
    val packetInReadOnly = packetIn.asSharedFlow()

    suspend fun emit(packet: Packet){
        packetIn.emit(packet)
    }

}