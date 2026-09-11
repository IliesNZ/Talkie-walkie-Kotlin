package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

interface IAudioRepository {

    suspend fun connectToUDP(ipAddress: String?)

    fun disconnectToUDP()

    suspend fun sendAudio(audioData: ByteArray)

    fun stopSendAudio()

}