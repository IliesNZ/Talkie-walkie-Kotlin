package com.iliesnz.talkie_walkie_kotlin.network.interfaces

import java.util.Objects
interface ITcpClient {
    fun ConnectToServer(ipAddress: String)

    fun DisconnectToServer()

    fun SendMessage(message: Objects)

    fun Listen()
}
