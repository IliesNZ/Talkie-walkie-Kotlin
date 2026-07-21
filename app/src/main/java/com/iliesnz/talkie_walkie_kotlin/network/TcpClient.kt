package com.iliesnz.talkie_walkie_kotlin.network

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.Objects

class TcpClient: ITcpClient {

    val port: Int = 48067

    private var socket: Socket? = null
    private var dataIn: BufferedReader? = null
    private var dataOut: PrintWriter? = null

    override fun ConnectToServer(ipAddress: String){
        socket = Socket(ipAddress, port)

        dataIn = BufferedReader(InputStreamReader(socket?.getInputStream()))
        dataOut = PrintWriter(socket?.getOutputStream(), true)
    }

    override fun DisconnectToServer() {
        dataIn?.close()
        dataOut?.close()
        socket?.close()
        dataIn = null
        dataOut = null
        socket = null
    }

    override fun SendMessage(message: Objects){
        dataOut?.println(message.toString())
    }

    override fun Listen() {
        while (socket?.isClosed == false) {
            val message = dataIn?.readLine()

            // TODO: Faire sortitr quelque part
        }
    }

}