package com.iliesnz.talkie_walkie_kotlin.repository

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.ISessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SessionRepository(private val applicationScope: CoroutineScope, private val tcpClient: ITcpClient, private val udpClient: IUdpClient) : ISessionRepository{

    override suspend fun connectToServer(ipAddress: String) = withContext(Dispatchers.IO) {

        println("tentative de connexion.")

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