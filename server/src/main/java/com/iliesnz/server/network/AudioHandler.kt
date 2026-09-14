package com.iliesnz.server.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.ByteBuffer

class AudioHandler {

    suspend fun audioServer() = withContext(Dispatchers.IO) {
        val serverPort = 48068
        val socket = DatagramSocket(serverPort)

        val receiveBuffer = ByteArray(2048)

        println("Serveur audio actif sur le port " + serverPort + " !")

        try {
            while (true) {
                val packet = DatagramPacket(receiveBuffer, receiveBuffer.size)
                socket.receive(packet)

                val data = packet.data

                if(packet.length >= 4){     // COde de session = Les 4 premier octets

                    // On récupère l'identifiant de l'utilisateur
                    val buffer = ByteBuffer.wrap(data, 0, 4)
                    val sessionCode = buffer.int
                    val clientAddress = packet.socketAddress

                    println("UDP >> Audio reçu du code : $sessionCode (${packet.length - 4} octets)")

                    // On récupère le sons produit par l'utilisateur
                    val client = SessionManager.getClient(sessionCode)
                    // On change l'address IP du client pour avoir la plus récente
                    SessionManager.addAddress(sessionCode, clientAddress as InetSocketAddress)

                    val clients = SessionManager.getClients()

                    //foreach clients
                    clients.forEach { (otherCode, otherClient) ->
                        if(otherClient.getChannel() == client?.getChannel() && otherCode != sessionCode && otherClient.getUdpInstance() != null){
                            val sendPacket = DatagramPacket(
                                data,
                                4,
                                packet.length - 4,
                                otherClient.getUdpInstance()
                            )
                            socket.send(sendPacket)
                        }
                    }

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            socket.close()
        }
    }
}

