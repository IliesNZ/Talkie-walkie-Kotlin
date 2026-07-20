package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface IHomeService {

    fun connectToServer(ipAddress: String)

    fun disconnectToServer()

}