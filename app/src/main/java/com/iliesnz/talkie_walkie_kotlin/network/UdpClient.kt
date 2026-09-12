package com.iliesnz.talkie_walkie_kotlin.network

import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer

class UdpClient(private val sessionManager: SessionManager): IUdpClient {

    private var socket: DatagramSocket = DatagramSocket()
    private var serverPort: Int = 48068


    override suspend fun sendAudio(audioData: ByteArray) = withContext(Dispatchers.IO) {

        val serverAddress: InetAddress? = InetAddress.getByName(sessionManager.getIpAddress())
        val sessionCode = sessionManager.getSessionCode() ?: return@withContext

        val buffer = ByteBuffer.allocate(4 + audioData.size)
        buffer.putInt(sessionCode)
        buffer.put(audioData)

        val packetData = buffer.array()     // On insert le code de la session et l'audio

        if (!socket.isClosed && serverAddress != null) {
            try {
                val packet = DatagramPacket(
                    packetData,
                    packetData.size,
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