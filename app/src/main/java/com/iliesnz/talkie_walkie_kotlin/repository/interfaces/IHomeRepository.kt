package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

import com.iliesnz.talkie_walkie_kotlin.network.TcpClient

interface IHomeRepository {

    fun connectToServer(ipAddress: String)

    fun disconnectToServer()

}