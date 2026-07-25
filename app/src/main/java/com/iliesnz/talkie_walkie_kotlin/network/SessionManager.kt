package com.iliesnz.talkie_walkie_kotlin.network


class SessionManager {
    private var sessionCode: Int? = null
    private var sessionChannel: Int? = null

    fun getSessionCode(): Int? {
        return sessionCode
    }

    fun getSessionChannel(): Int? {
        return sessionChannel
    }

    fun setSessionChannel(channel: Int?) {
        this.sessionChannel = channel
    }

    fun setSessionCode(code: Int?) {
        this.sessionCode = code
    }
}