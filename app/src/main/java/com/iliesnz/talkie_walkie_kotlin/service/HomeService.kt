package com.iliesnz.talkie_walkie_kotlin.service

import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IHomeService

class HomeService (private val repository: IHomeRepository): IHomeService {

    override fun verifyDomain(ipAddress: String){

        if(ipAddress.isEmpty()){
            throw IllegalArgumentException("IP address cannot be empty")
        }

        repository.verifyDomain(ipAddress)

    }

    override fun disconnectToServer() {
        repository.disconnectToServer()
    }

}