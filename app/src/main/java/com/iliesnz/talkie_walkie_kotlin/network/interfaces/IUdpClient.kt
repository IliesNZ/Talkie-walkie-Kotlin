package com.iliesnz.talkie_walkie_kotlin.network.interfaces

interface IUdpClient {

    suspend fun startCommunication(serverAddress: String?)

    fun stopCommunication()

}