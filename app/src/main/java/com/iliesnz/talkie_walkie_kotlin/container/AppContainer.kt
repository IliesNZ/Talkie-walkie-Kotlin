package com.iliesnz.talkie_walkie_kotlin.container

import com.iliesnz.talkie_walkie_kotlin.TalkieWalkieApplication
import com.iliesnz.talkie_walkie_kotlin.network.TcpClient
import com.iliesnz.talkie_walkie_kotlin.network.UdpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.viewmodel.HomeViewmodel
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IHomeService
import com.iliesnz.talkie_walkie_kotlin.service.HomeService
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository
import com.iliesnz.talkie_walkie_kotlin.repository.HomeRepository
import kotlinx.coroutines.CoroutineScope

class AppContainer(applicationScope: CoroutineScope) {       //Utilisation pour le model MVVM => passage des instances en paramètre à partir de la view

    private val tcpClient: ITcpClient = TcpClient()

    private val udpClient: IUdpClient = UdpClient()

    private val homeRepository: IHomeRepository = HomeRepository(applicationScope, tcpClient, udpClient)

    private val homeService: IHomeService = HomeService(homeRepository)

    val homeViewModel = HomeViewmodel(homeService)

}