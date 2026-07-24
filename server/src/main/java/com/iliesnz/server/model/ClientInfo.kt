package com.iliesnz.server.model

import kotlinx.coroutines.channels.Channel
import java.net.InetSocketAddress
import kotlin.time.Instant

class ClientInfo {

    private var channel: Int = 1
    private var udpInstance: InetSocketAddress?= null

    constructor(channel: Int, udpInstance: InetSocketAddress) {
        this.channel = channel
        this.udpInstance = udpInstance
    }

    fun getChannel(): Int = channel

    fun setChannel(channel: Int) {
        this.channel = channel
    }

    fun getUdpInstance(): InetSocketAddress? = udpInstance

    fun setUdpInstance(udpInstance: InetSocketAddress?) {
        this.udpInstance = udpInstance
    }
}