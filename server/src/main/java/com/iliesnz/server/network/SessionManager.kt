package com.iliesnz.server.network

import com.iliesnz.server.model.ClientInfo
import com.iliesnz.shared.model.Session
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap


object SessionManager {

    private val clients = ConcurrentHashMap<Int, ClientInfo>()

    fun addClient(code: Int, channel: Int) {
        clients[code] = ClientInfo(channel)
    }

    fun addAddress(code: Int, address: InetSocketAddress) {
        clients[code]?.setUdpInstance(address)
    }

    fun getClient(code: Int): ClientInfo? = clients[code]

    fun removeClient(code: Int) {
        clients.remove(code)
    }

    fun changeClientChannel(session: Session) {
        val code = session.getId()
        val channel = session.getChannel()

        clients[code]?.setChannel(channel)
    }
}