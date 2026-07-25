package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

import com.iliesnz.shared.model.Session
import kotlinx.coroutines.Job

interface ISessionRepository {

    suspend fun connectToServer(ipAddress: String): Job

    suspend fun disconnectToServer()

    suspend fun changeChannel(session: Session)

}