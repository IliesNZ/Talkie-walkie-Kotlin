package com.iliesnz.server.network

import com.iliesnz.server.model.ClientInfo
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap


class SessionManager {

    private val clients = ConcurrentHashMap<Int, ClientInfo>()

    fun addClient(id: Int, channel: Int, udpAddress: InetSocketAddress) {
        clients[id] = ClientInfo(channel, udpAddress)
    }

    fun getClient(id: Int): ClientInfo? = clients[id]

    fun removeClient(id: Int) {
        clients.remove(id)
    }
}