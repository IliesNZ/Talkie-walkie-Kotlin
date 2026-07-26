package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

import com.iliesnz.shared.model.Session
import kotlinx.coroutines.Job

interface ISessionRepository {

    suspend fun connectToTCP(ipAddress: String): Job

    suspend fun disconnectToTCP()

    suspend fun changeChannel(session: Session)

    suspend fun startCommunication(ipAddress: String?)

    fun stopCommunication()

}