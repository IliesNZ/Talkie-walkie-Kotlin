package com.iliesnz.talkie_walkie_kotlin.service.interfaces

interface IAudioService {

    suspend fun startCommunication()

    fun stopCommunication()


}