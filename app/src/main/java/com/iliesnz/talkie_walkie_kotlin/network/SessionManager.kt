package com.iliesnz.talkie_walkie_kotlin.network


class SessionManager {
    private var sessionCode: Int? = null
    private var sessionChannel: Int? = null

    private var ipAddress: String? = null

    fun cleanSession(){
        sessionCode = null
        sessionChannel = null
        ipAddress = null
    }

    fun getSessionCode(): Int? {
        return sessionCode
    }

    fun getSessionChannel(): Int? {
        return sessionChannel
    }

    fun getIpAddress(): String?{
        return ipAddress
    }

    fun setSessionChannel(channel: Int?) {
        this.sessionChannel = channel
    }

    fun setSessionCode(code: Int?) {
        this.sessionCode = code
    }

    fun setIpAddress(ipAddress: String){
        this.ipAddress = ipAddress
    }
}