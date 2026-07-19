package com.iliesnz.talkie_walkie_kotlin.repository.interfaces

interface IHomeRepository {

    fun verifyDomain(ipAddress: String)

    fun disconnectToServer()

}