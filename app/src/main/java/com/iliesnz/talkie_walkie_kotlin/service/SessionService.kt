package com.iliesnz.talkie_walkie_kotlin.service

import com.iliesnz.shared.model.Session
import com.iliesnz.talkie_walkie_kotlin.network.SessionManager
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.ISessionRepository
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.ISessionService

class SessionService (private val repository: ISessionRepository, private val sessionManager: SessionManager): ISessionService {

    override suspend fun connectToTCP(ipAddress: String){

        if(ipAddress.isEmpty()){
            throw IllegalArgumentException("IP address cannot be empty")
        }

        sessionManager.setIpAddress(ipAddress)
        repository.connectToTCP(ipAddress)

    }

    override suspend fun disconnectToTCP() {
        repository.disconnectToTCP()
        sessionManager.cleanSession()
    }

    override suspend fun changeChannel(channel: Int) {
        if(channel >= 1 && channel <= 15){
            sessionManager.setSessionChannel(channel)

            val sessionCode: Int? = sessionManager.getSessionCode()
            val sessionChannel: Int? = sessionManager.getSessionChannel()

            if (sessionCode != null && sessionChannel != null) {
                repository.changeChannel(Session(sessionCode, sessionChannel))
            }
        }
        else{
            throw IllegalArgumentException("Channel incorrect")
        }
    }

    override fun changeSessionCode(code: Int){
        sessionManager.setSessionCode(code)
    }

    override suspend fun startCommunication() {
        val ipAddress = sessionManager.getIpAddress()

        repository.startCommunication(ipAddress)
    }

    override fun stopCommunication() {
        repository.stopCommunication()
    }

}