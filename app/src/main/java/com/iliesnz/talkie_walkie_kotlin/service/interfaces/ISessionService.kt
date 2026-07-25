package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface ISessionService {

    suspend fun connectToServer(ipAddress: String)

    suspend fun disconnectToServer()

    suspend fun changeChannel(channel: Int)

    fun changeSessionCode(code: Int)

}