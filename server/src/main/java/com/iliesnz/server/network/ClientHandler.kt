package com.iliesnz.server.network

import com.google.gson.Gson
import com.iliesnz.server.service.interfaces.ISessionService
import com.iliesnz.server.service.SessionService
import com.iliesnz.shared.model.Packet
import com.iliesnz.shared.model.Session
import com.iliesnz.shared.protocol.Request
import com.iliesnz.shared.protocol.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandler(private val client: Socket) : Runnable {

    val gson = Gson()

    val dataIn = BufferedReader(InputStreamReader(client.getInputStream()))
    val dataOut = PrintWriter(client.getOutputStream(), true)

    val sessionService: ISessionService = SessionService()

    override fun run() {
        communication()
    }

    fun communication(){

        println("Connexion établis !")

        while (true){

            val json = dataIn.readLine() ?: break
            val packetIn: Packet = toPacket(json) as Packet

            val packetOut = when (packetIn?.getType()){

                Request.CREATE_SESSION.name -> {

                    val code = sessionService.createCode()
                    SessionManager.addClient(code, 1)

                    Packet(Response.RETURN_SESSION.name, code)
                }

                Request.CHANGE_CHANNEL.name -> {
                    val session = gson.fromJson(gson.toJsonTree(packetIn.getData()), Session::class.java)

                    if (session != null) {
                        println("Nouveau channel = " + session.getChannel())
                        SessionManager.changeClientChannel(session)
                    }

                    Packet(Response.OK.name, "")
                }

                else -> Packet(Response.INVALID_REQUEST.name, "Requête introuvable.")
            }
            sendMessage(packetOut)
        }
    }

    fun sendMessage(message: Packet){
        dataOut?.println(toJson(message))
    }

    fun toJson(packet: Packet): String{
        return gson.toJson(packet)
    }

    fun toPacket(data: String): Packet {
        return gson.fromJson(data, Packet::class.java)
    }
}