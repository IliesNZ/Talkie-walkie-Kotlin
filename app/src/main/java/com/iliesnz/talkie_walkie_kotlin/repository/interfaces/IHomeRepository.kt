package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

import com.iliesnz.talkie_walkie_kotlin.network.TcpClient
import kotlinx.coroutines.Job

interface IHomeRepository {

    suspend fun connectToServer(ipAddress: String): Job

    suspend fun disconnectToServer()

}