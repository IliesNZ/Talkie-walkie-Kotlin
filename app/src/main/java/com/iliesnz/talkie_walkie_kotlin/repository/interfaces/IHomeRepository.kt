package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

interface IHomeRepository {

    fun connectToServer(ipAddress: String)

    fun disconnectToServer()

}