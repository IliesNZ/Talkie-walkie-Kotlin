package com.iliesnz.talkie_walkie_kotlin.repository

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeRepository(private val applicationScope: CoroutineScope, private val tcpClient: ITcpClient, private val udpClient: IUdpClient) : IHomeRepository{

    override suspend fun connectToServer(ipAddress: String) = withContext(Dispatchers.IO) {
        try {
            tcpClient.connectToServer(ipAddress)
            applicationScope.launch {
                tcpClient.listen()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun disconnectToServer() = withContext(Dispatchers.IO) {
        try {
            tcpClient.disconnectToServer()
        }
        catch (e: Exception){
            e.printStackTrace()
            throw e
        }
    }
}