package com.iliesnz.talkie_walkie_kotlin.service

import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IHomeService

class HomeService (private val repository: IHomeRepository): IHomeService {

    override fun connectToServer(ipAddress: String){

        if(ipAddress.isEmpty()){
            throw IllegalArgumentException("IP address cannot be empty")
        }

        repository.connectToServer(ipAddress)

    }

    override fun disconnectToServer() {
        repository.disconnectToServer()
    }

}