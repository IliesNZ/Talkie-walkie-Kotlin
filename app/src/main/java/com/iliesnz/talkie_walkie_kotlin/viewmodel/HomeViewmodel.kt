package com.iliesnz.talkie_walkie_kotlin.viewmodel

import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IHomeService
import java.net.ConnectException
import java.net.SocketTimeoutException

class HomeViewmodel(private val service: IHomeService) {

    fun connectToServer(ipAddress: String) {
        try {
            service.connectToServer(ipAddress)
            TODO("Mettre des state.value à la place dans les catch")
        } catch (e: ConnectException) {
            e.printStackTrace()
        } catch (e: SocketTimeoutException){
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}