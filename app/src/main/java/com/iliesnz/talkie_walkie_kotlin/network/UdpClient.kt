package com.iliesnz.talkie_walkie_kotlin.network

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UdpClient: IUdpClient {

    override suspend fun connectToServer(ipAddress: String) {

    }

    override fun disconnectToServer() {

    }

    override suspend fun sendAudio(audioData: ByteArray) = withContext(Dispatchers.IO) {

    }

    override fun stopSendAudio(){

    }

}