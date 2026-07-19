package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface IHomeService {

    fun verifyDomain(ipAddress: String)

    fun disconnectToServer()

}