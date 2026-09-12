package com.iliesnz.talkie_walkie_kotlin.service

import android.Manifest
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IAudioRepository
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IAudioService
import com.iliesnz.talkie_walkie_kotlin.service.sharedFlow.AudioHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioService(private val repository: IAudioRepository, private val audioHandler: AudioHandler): IAudioService {

    @Volatile var isRecording = false

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    override suspend fun startCommunication() = withContext(Dispatchers.IO) {     //On arrète d'écouter pour parler (une seule personne peut parler à la fois)

        if (isRecording) return@withContext  // Si l'utilisateur spam le bouton => Cela empêche de créé de nouveauc flux audio

        val sampleRate = 16000
        val channelMask = AudioFormat.CHANNEL_IN_MONO
        val encoding = AudioFormat.ENCODING_PCM_16BIT

        val minBuffSizeBytes = AudioRecord.getMinBufferSize(sampleRate, channelMask, encoding)

        val audioRecord = AudioRecord.Builder()
            .setAudioSource(MediaRecorder.AudioSource.MIC)
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(encoding)
                    .setSampleRate(sampleRate)
                    .setChannelMask(channelMask)
                    .build()
            )
            .setBufferSizeInBytes(2 * minBuffSizeBytes)
            .build()

        val buffer = ByteArray(minBuffSizeBytes)

        isRecording = true
        audioRecord.startRecording()

        try {
            while (isRecording) {
                val bytesRead = audioRecord.read(buffer, 0, buffer.size)

                if (bytesRead > 0) {
                    val audioData = buffer.copyOf(bytesRead)
                    repository.sendAudio(audioData)
                }
            }
        } finally {
            audioRecord.stop()
            audioRecord.release()
        }

    }

    override fun stopCommunication() {
        isRecording = false
    }

    override suspend fun listenUDP() {
        repository.listenUDP()
    }

    override suspend fun listenAudio() = withContext(Dispatchers.IO) {
        val sampleRate = 16000
        val channelMask = AudioFormat.CHANNEL_OUT_MONO // CHANNEL_OUT pour les haut-parleurs
        val encoding = AudioFormat.ENCODING_PCM_16BIT

        val minBuffSize = AudioTrack.getMinBufferSize(sampleRate, channelMask, encoding)

        val audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build())
            .setAudioFormat(AudioFormat.Builder()
                .setEncoding(encoding)
                .setSampleRate(sampleRate)
                .setChannelMask(channelMask)
                .build())
            .setBufferSizeInBytes(minBuffSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        audioTrack.play()

        try {
            audioHandler.audioInReadOnly.collect { audioData ->
                // On écrit les données dans le haut-parleur
                audioTrack.write(audioData, 0, audioData.size)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            audioTrack.stop()
            audioTrack.release()
        }
    }
}