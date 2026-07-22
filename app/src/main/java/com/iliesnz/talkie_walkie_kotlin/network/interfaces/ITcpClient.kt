package com.iliesnz.talkie_walkie_kotlin.network.interfaces

import com.iliesnz.talkie_walkie_kotlin.shared.model.Packet
import java.util.Objects
interface ITcpClient {
    fun connectToServer(ipAddress: String)

    fun disconnectToServer()

    fun sendMessage(message: Packet)

    suspend fun listen()

    fun toJson(packet: Packet): String

    fun toPacket(data: String): Packet?
}
