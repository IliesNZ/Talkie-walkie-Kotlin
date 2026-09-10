package com.iliesnz.talkie_walkie_kotlin.network

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UdpClient(): IUdpClient {

    var stop = false

    override suspend fun startCommunication(serverAddress: String?) = withContext(Dispatchers.IO) {

    }

    override fun stopCommunication(){

    }

}