package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface IAudioService {

    suspend fun startCommunication()

    fun stopCommunication()

    suspend fun listenUDP()

    suspend fun listenAudio(audioData: ByteArray)

    suspend fun identification()

}