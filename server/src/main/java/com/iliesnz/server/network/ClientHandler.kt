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

class ClientHandler(client: Socket) {

    val gson = Gson()

    val dataIn = BufferedReader(InputStreamReader(client.getInputStream()))
    val dataOut = PrintWriter(client.getOutputStream(), true)

    val sessionService: ISessionService = SessionService()

    fun communication(){

        println("Connexion établis !")

        while (true){

            val json = dataIn.readLine() ?: break
            val packetIn: Packet? = toPacket(json)

            val packetOut = when (packetIn?.getType()){

                Request.CREATE_SESSION.name -> {

                    val code = sessionService.createCode()
                    val session: Session = Session(code, 1)
                    //Mise en place d'un map pour retenir le code de chaque client avec leur handler.

                    Packet(Response.RETURN_SESSION.name, code)
                }

                Request.CHANGE_CHANNEL.name -> {

                    //Changement du numéro de channel dans le map

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

    fun toPacket(data: String): Packet? {
        return gson.fromJson(data, Packet::class.java)
    }
}