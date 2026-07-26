package com.iliesnz.talkie_walkie_kotlin.network

import android.media.AudioFormat
import android.system.Os.socket
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import io.github.jaredmdobson.concentus.OpusApplication
import io.github.jaredmdobson.concentus.OpusEncoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket

class UdpClient(): IUdpClient {

    var stop = false

    override suspend fun startCommunication(serverAddress: String?) = withContext(Dispatchers.IO) {
        val format = AudioFormat(48000f, 16, 1, true, false)

        val mic = AudioSystem.getTargetDataLine(format)
        mic.open(format)
        mic.start()

        val encoder = OpusEncoder(48000, 1, OpusApplication.OPUS_APPLICATION_VOIP)

        val pcmBuffer = ByteArray(1920)
        val opusBuffer = ByteArray(1275)

        stop = false

        while (!stop){

            val bytesRead = mic.read(pcmBuffer, 0, pcmBuffer.size)

            val bytesEncoded = encoder.encode(
                pcmBuffer, 0, 960,
                opusBuffer, 0, opusBuffer.size
            )

            val packet = DatagramPacket(opusBuffer, bytesEncoded, serverAddress, 48068)
            socket.send(packet)

        }

    }

    override fun stopCommunication(){
        stop = true
    }

}