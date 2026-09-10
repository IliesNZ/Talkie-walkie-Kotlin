package com.iliesnz.server.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

class AudioHandler {


    suspend fun audioServer() = withContext(Dispatchers.IO) {
        // 1. Initialisation de la Socket UDP
        val socket = DatagramSocket(48068)
        val udpBuffer = ByteArray(1024) // Reçoit les paquets Opus compressés

        // 2. Configuration du format audio (48 kHz, 16 bits, Mono)
        val format = AudioFormat(48000f, 16, 1, true, false)

        // 3. Configuration des haut-parleurs (SourceDataLine = Sortie audio)
        val speakers = AudioSystem.getLine(
            javax.sound.sampled.DataLine.Info(SourceDataLine::class.java, format)
        ) as SourceDataLine
        speakers.open(format)
        speakers.start() // Allume le flux vers les haut-parleurs

        // 4. Initialisation du décodeur Opus
        val decoder = OpusDecoder(48000, 1) // 48kHz, Mono

        // Buffer pour stocker le son décompressé (PCM)
        // 20ms de son = 960 échantillons = 1920 octets (car 16 bits = 2 octets par échantillon)
        val pcmBuffer = ByteArray(1920)

        println("Serveur audio prêt, en attente de flux...")

        try {
            while (true) {
                // A. Attente du paquet UDP (Opus compressé)
                val packet = DatagramPacket(udpBuffer, udpBuffer.size)
                socket.receive(packet)
                println("J'entends !")

                // B. Décodage d'Opus vers PCM brut
                // frameSize = 960 (échantillons attendus pour 20ms à 48kHz)
                val samplesDecoded = decoder.decode(
                    packet.data, 0, packet.length, // Entrée : paquet UDP
                    pcmBuffer, 0, 960,             // Sortie : buffer PCM
                    false                           // Pas de FEC (Forward Error Correction)
                )

                // C. Calcul du nombre d'octets décompressés (1 échantillon mono 16 bits = 2 octets)
                val bytesToPlay = samplesDecoded * 2

                // D. Envoi du son PCM décompressé vers les haut-parleurs (Bloquant)
                speakers.write(pcmBuffer, 0, bytesToPlay)
            }
        } finally {
            // Nettoyage si la coroutine est annulée
            speakers.drain()
            speakers.close()
            socket.close()
        }
    }
}

