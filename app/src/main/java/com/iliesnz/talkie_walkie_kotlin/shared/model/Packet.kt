package com.iliesnz.talkie_walkie_kotlin.shared.model

import com.iliesnz.talkie_walkie_kotlin.shared.protocol.Request
import java.io.Serializable

data class Packet(val type: Request, val data: String) : Serializable {



}
