package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface IHomeService {

    suspend fun connectToServer(ipAddress: String)

    suspend fun disconnectToServer()

}