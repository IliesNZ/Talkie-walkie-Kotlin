package com.iliesnz.talkie_walkie_kotlin.network

import com.iliesnz.shared.model.Packet
import com.google.gson.Gson
import com.iliesnz.shared.protocol.Request
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow.PacketHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class TcpClient(private val packetHandler: PacketHandler): ITcpClient {

    val port: Int = 48067

    private val gson = Gson()

    private var socket: Socket? = null
    private var dataIn: BufferedReader? = null
    private var dataOut: PrintWriter? = null

    override suspend fun connectToServer(ipAddress: String) = withContext(Dispatchers.IO) {
        socket = Socket(ipAddress, port)

        dataIn = BufferedReader(InputStreamReader(socket?.getInputStream()))
        dataOut = PrintWriter(socket?.getOutputStream(), true)

        val packet = Packet(Request.CREATE_SESSION.name, "Bonjour")
        sendMessage(packet)
    }

    override fun disconnectToServer() {
        dataIn?.close()
        dataOut?.close()
        socket?.close()
        dataIn = null
        dataOut = null
        socket = null
    }

    override suspend fun sendMessage(message: Packet){
        dataOut?.println(toJson(message))
    }

    override suspend fun listen() = withContext(Dispatchers.IO) {

        val s = socket
        while (s != null && s.isConnected && !s.isClosed) {

            val message: String = dataIn?.readLine() ?: break
            val packet = toPacket(message)

            if (packet is Packet){
                packetHandler.emit(packet)
            }
        }
    }

    override fun toJson(packet: Packet): String{
        val jsonString: String = gson.toJson(packet)
        return jsonString
    }

    override fun toPacket(data: String): Packet? {
        val packet: Packet? = gson.fromJson(data, Packet::class.java)
        return packet
    }

}