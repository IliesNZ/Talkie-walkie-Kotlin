package com.iliesnz.talkie_walkie_kotlin.service

import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.ISessionRepository
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.ISessionService

class SessionService (private val repository: ISessionRepository): ISessionService {

    override suspend fun connectToServer(ipAddress: String){

        if(ipAddress.isEmpty()){
            throw IllegalArgumentException("IP address cannot be empty")
        }

        repository.connectToServer(ipAddress)

    }

    override suspend fun disconnectToServer() {
        repository.disconnectToServer()
    }

}