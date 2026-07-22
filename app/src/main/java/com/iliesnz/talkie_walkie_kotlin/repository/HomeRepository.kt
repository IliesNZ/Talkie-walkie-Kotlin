package com.iliesnz.talkie_walkie_kotlin.repository

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeRepository(val applicationScope: CoroutineScope, private val tcpClient: ITcpClient, private val udpClient: IUdpClient) : IHomeRepository{

    override fun connectToServer(ipAddress: String) {
        applicationScope.launch {
            withContext(Dispatchers.IO){
                try {
                    tcpClient.connectToServer(ipAddress)
                    tcpClient.listen()
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

    }

    override fun disconnectToServer() {
        applicationScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    tcpClient.disconnectToServer()
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

}