package com.iliesnz.talkie_walkie_kotlin.repository

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IAudioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AudioRepository(private val applicationScope: CoroutineScope, private val udpClient: IUdpClient): IAudioRepository {

    override suspend fun sendAudio(audioData: ByteArray) {
        try {
            udpClient.sendAudio(audioData)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun listenUDP() {
        applicationScope.launch {
            try {
                udpClient.listen()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}