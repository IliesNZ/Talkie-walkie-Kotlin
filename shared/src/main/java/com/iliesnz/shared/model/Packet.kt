package com.iliesnz.shared.model

import com.iliesnz.shared.protocol.Request
import java.io.Serializable

data class Packet(val type: Request, val data: String) : Serializable {

    fun getType(): String{
        return this.type.name
    }

}
