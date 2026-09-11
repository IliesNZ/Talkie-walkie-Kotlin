package com.iliesnz.talkie_walkie_kotlin.network.interfaces

interface IUdpClient {

    suspend fun connectToServer(ipAddress: String)

    fun disconnectToServer()

    suspend fun sendAudio(audioData: ByteArray)

    fun stopSendAudio()

}