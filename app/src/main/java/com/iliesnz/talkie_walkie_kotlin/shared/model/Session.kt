package com.iliesnz.talkie_walkie_kotlin.shared.model

import kotlinx.coroutines.channels.Channel

class Session {

    private var id: String? = null
    private var channel: Int? = null

    fun Session(id: String, channel: Int){
        this.id = id
        this.channel = channel
    }

    fun setChannel(channel: Int){
        this.channel = channel
    }

    fun getChannel(): Int? {
        return this.channel
    }

    fun getId(): String? {
        return this.id
    }

}