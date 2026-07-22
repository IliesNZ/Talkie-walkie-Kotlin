package com.iliesnz.server


import com.google.gson.Gson
import com.iliesnz.shared.model.Packet
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

    fun communication(){

        while (true){

            val json = dataIn.readLine() ?: break
            val packetIn: Packet? = toPacket(json)

            val packetOut: Packet

            when (packetIn?.getType()){

                Request.CREATE_SESSION.name -> {


                    //packetOut = Packet(Response.RETURN_SESSION.name, "Bonjour")
                    //sendMessage(packetOut)
                }

                Request.CHANGE_CHANNEL.name -> {


                    Response.OK.name
                }

                else -> Response.INVALID_REQUEST.name
            }
        }
    }

    fun sendMessage(message: Packet){
        dataOut?.println(toJson(message))
    }

    fun toJson(packet: Packet): String{
        val jsonString: String = gson.toJson(packet)
        return jsonString
    }

    fun toPacket(data: String): Packet? {
        val packet: Packet? = gson.fromJson(data, Packet::class.java)
        return packet
    }
}