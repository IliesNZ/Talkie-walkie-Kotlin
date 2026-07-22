package com.iliesnz.talkie_walkie_kotlin.network.interfaces

import com.iliesnz.shared.model.Packet

interface ITcpClient {
    suspend fun connectToServer(ipAddress: String)

    fun disconnectToServer()

    suspend fun sendMessage(message: Packet)

    suspend fun listen()

    fun toJson(packet: Packet): String

    fun toPacket(data: String): Packet?
}
