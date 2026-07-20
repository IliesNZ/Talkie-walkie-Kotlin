package com.iliesnz.talkie_walkie_kotlin.repository

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository

class HomeRepository(tcpClient: ITcpClient, udpClient: IUdpClient) : IHomeRepository{

    override fun connectToServer(ipAddress: String) {
        TODO("Connexion au serveur")
    }

    override fun disconnectToServer() {
        TODO("Déconnexion au serveur")
    }

}