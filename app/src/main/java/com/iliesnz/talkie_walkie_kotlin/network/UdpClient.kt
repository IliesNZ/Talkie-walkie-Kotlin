package com.iliesnz.talkie_walkie_kotlin.network

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UdpClient(private val sessionManager: SessionManager): IUdpClient {

    private var socket: DatagramSocket? = null
    private var serverAddress: InetAddress? = sessionManager.getIpAddress() as InetAddress?
    private var serverPort: Int = 48068


    override suspend fun sendAudio(audioData: ByteArray) = withContext(Dispatchers.IO) {

        if (socket != null && !socket.isClosed && serverAddress != null) {
            try {
                val packet = DatagramPacket(
                    audioData,
                    audioData.size,
                    serverAddress,
                    serverPort
                )

                socket.send(packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}