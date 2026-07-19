package com.iliesnz.talkie_walkie_kotlin.service.interfaces

import android.content.pm.verify.domain.DomainVerificationManager
import android.location.Address

interface HomeService {
    fun domainVerificationManager(ipAddress: String)
}