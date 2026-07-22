package com.iliesnz.talkie_walkie_kotlin.network

import com.google.gson.Gson
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.shared.model.Packet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class TcpClient: ITcpClient {

    val port: Int = 48067

    private val gson = Gson()

    private var socket: Socket? = null
    private var dataIn: BufferedReader? = null
    private var dataOut: PrintWriter? = null

    override fun connectToServer(ipAddress: String){
        socket = Socket(ipAddress, port)

        dataIn = BufferedReader(InputStreamReader(socket?.getInputStream()))
        dataOut = PrintWriter(socket?.getOutputStream(), true)
    }

    override fun disconnectToServer() {
        dataIn?.close()
        dataOut?.close()
        socket?.close()
        dataIn = null
        dataOut = null
        socket = null
    }

    override fun sendMessage(message: Packet){
        dataOut?.println(toJson(message))
    }

    suspend override fun listen() = withContext(Dispatchers.IO) {

        val s = socket
        while (s != null && s.isConnected && !s.isClosed) {

            val message: String? = dataIn?.readLine() ?: break
            if (message != null) {
                val packet = toPacket(message)
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