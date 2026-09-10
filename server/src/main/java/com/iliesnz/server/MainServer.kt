package com.iliesnz.server


import com.iliesnz.server.network.AudioHandler
import com.iliesnz.server.network.ClientHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket

fun main() = runBlocking(Dispatchers.IO) {

    val port = 48067
    val serverSocket = ServerSocket(port)

    launch {
        val audioHandler = AudioHandler()
        audioHandler.audioServer()
    }

    println("Serveur sur le port " + port)

    while(true){
        val client = serverSocket.accept()
        launch {
            val handler = ClientHandler(client)
            handler.run()
        }
    }

}
