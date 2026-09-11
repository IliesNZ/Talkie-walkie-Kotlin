package com.iliesnz.talkie_walkie_kotlin.service

import com.iliesnz.talkie_walkie_kotlin.network.SessionManager
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.ISessionRepository
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IAudioService

class AudioService(private val repository: ISessionRepository, private val sessionManager: SessionManager): IAudioService {

    override suspend fun connectToUDP() {
        val ipAddress: String? = sessionManager.getIpAddress()
        repository.connectToUDP(ipAddress)
    }

    override fun disconnectToUDP() {
        repository.disconnectToUDP()
    }

    override suspend fun startCommunication() {
        repository.startCommunication()
    }

    override fun stopCommunication() {
        repository.stopCommunication()
    }

}