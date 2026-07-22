package com.iliesnz.server


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket

fun main() = runBlocking(Dispatchers.IO) {

    val port = 48067
    val serverSocket = ServerSocket(port)
    println("Serveur sur le port " + port)

    while(true){
        val client = serverSocket.accept()
        launch {
            val handler = ClientHandler(client)
            handler.communication()
        }
    }

}