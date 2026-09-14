package com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AudioHandler {

    private val audioIn = MutableSharedFlow<ByteArray>(replay = 0, extraBufferCapacity = 10)
    val audioInReadOnly = audioIn.asSharedFlow()

    suspend fun emit(packet: ByteArray){
        audioIn.emit(packet)
    }

}