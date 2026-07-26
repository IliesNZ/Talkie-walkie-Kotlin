package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface ISessionService {

    suspend fun connectToTCP(ipAddress: String)

    suspend fun disconnectToTCP()

    suspend fun changeChannel(channel: Int)

    fun changeSessionCode(code: Int)

    suspend fun startCommunication()

    fun stopCommunication()

}