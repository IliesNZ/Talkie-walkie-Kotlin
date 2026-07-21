package com.iliesnz.talkie_walkie_kotlin.repository

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository

class HomeRepository(private val tcpClient: ITcpClient, private val udpClient: IUdpClient) : IHomeRepository{

    override fun connectToServer(ipAddress: String) {
        tcpClient.ConnectToServer(ipAddress)
    }



    override fun disconnectToServer() {
        tcpClient.DisconnectToServer()
    }

}