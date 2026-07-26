package com.iliesnz.talkie_walkie_kotlin.network.interfaces

interface IUdpClient {

    suspend fun startCommunication(ipAddress: String?)

    fun stopCommunication()

}