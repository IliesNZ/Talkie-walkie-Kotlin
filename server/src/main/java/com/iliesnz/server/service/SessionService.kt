package com.iliesnz.server.service

import com.iliesnz.server.service.interfaces.ISessionService
import kotlin.random.Random

class SessionService: ISessionService {

    override fun createCode(): Int{
        val code: Int = Random.nextInt(100000000, 999999999)

        return code
    }

}