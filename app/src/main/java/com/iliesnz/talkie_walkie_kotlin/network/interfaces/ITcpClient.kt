package com.iliesnz.talkie_walkie_kotlin.network.interfaces

import java.util.Objects
interface ITcpClient {
    fun connectToServer(ipAddress: String)

    fun disconnectToServer()

    fun sendMessage(message: Any)

    suspend fun listen()
}
