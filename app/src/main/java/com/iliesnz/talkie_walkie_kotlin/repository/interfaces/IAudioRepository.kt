package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

interface IAudioRepository {

    suspend fun sendAudio(audioData: ByteArray)

    suspend fun listenUDP()

}