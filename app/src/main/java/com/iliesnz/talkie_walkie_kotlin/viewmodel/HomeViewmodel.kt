package com.iliesnz.talkie_walkie_kotlin.viewmodel

import com.iliesnz.talkie_walkie_kotlin.service.interfaces.HomeService
import java.net.ConnectException
import java.net.SocketTimeoutException

class HomeViewmodel(private val service: HomeService) {

    fun domainVerification(ipAddress: String) {
        try {
            service.domainVerificationManager(ipAddress)
        } catch (e: ConnectException) {
            e.printStackTrace()
        } catch (e: SocketTimeoutException){
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}