package com.iliesnz.server.model

import java.net.InetSocketAddress

class ClientInfo {

    private var channel: Int = 1
    private var udpInstance: InetSocketAddress?= null

    constructor(channel: Int) {
        this.channel = channel
        this.udpInstance = null
    }

    fun getChannel(): Int = channel

    fun setChannel(channel: Int?) {
        if (channel != null) {
            this.channel = channel
        }
    }

    fun getUdpInstance(): InetSocketAddress? = udpInstance

    fun setUdpInstance(udpInstance: InetSocketAddress?) {
        this.udpInstance = udpInstance
    }
}