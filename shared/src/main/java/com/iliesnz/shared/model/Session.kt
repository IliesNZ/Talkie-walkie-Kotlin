package com.iliesnz.shared.model

class Session {

    private var id: Int? = null
    private var channel: Int? = null

    constructor(id: Int, channel: Int){
        this.id = id
        this.channel = channel
    }

    fun setChannel(channel: Int){
        this.channel = channel
    }

    fun getChannel(): Int? {
        return this.channel
    }

    fun getId(): Int? {
        return this.id
    }

}