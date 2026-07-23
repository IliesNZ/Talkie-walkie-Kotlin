package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

import kotlinx.coroutines.Job

interface ISessionRepository {

    suspend fun connectToServer(ipAddress: String): Job

    suspend fun disconnectToServer()

}