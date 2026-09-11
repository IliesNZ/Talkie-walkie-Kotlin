package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface IAudioService {

    suspend fun connectToUDP()

    suspend fun startCommunication()

    fun stopCommunication()

    fun disconnectToUDP()

}