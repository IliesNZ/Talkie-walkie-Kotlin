package com.iliesnz.shared.model

import com.iliesnz.shared.protocol.Request
import java.io.Serializable

data class Packet(private val type: String,private val data: Any) : Serializable {

    fun getType(): String {
        return this.type
    }

    fun getData(): Any{
        return this.data
    }

}
