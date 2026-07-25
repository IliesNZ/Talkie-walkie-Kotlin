package com.iliesnz.talkie_walkie_kotlin.container

import com.iliesnz.talkie_walkie_kotlin.network.SessionManager
import com.iliesnz.talkie_walkie_kotlin.network.TcpClient
import com.iliesnz.talkie_walkie_kotlin.network.UdpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.ITcpClient
import com.iliesnz.talkie_walkie_kotlin.network.interfaces.IUdpClient
import com.iliesnz.talkie_walkie_kotlin.viewmodel.HomeViewmodel
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.ISessionService
import com.iliesnz.talkie_walkie_kotlin.service.SessionService
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.ISessionRepository
import com.iliesnz.talkie_walkie_kotlin.repository.SessionRepository
import com.iliesnz.talkie_walkie_kotlin.viewmodel.TalkieViewModel
import com.iliesnz.talkie_walkie_kotlin.viewmodel.sharedFlow.PacketHandler
import kotlinx.coroutines.CoroutineScope

class AppContainer(applicationScope: CoroutineScope) {       //Utilisation pour le model MVVM => passage des instances en paramètre à partir de la view

    private val sessionManager = SessionManager()
    private val packetHandler = PacketHandler()
    private val tcpClient: ITcpClient = TcpClient(packetHandler)
    private val udpClient: IUdpClient = UdpClient()

    private val sessionRepository: ISessionRepository = SessionRepository(applicationScope, tcpClient, udpClient)
    private val sessionService: ISessionService = SessionService(sessionRepository, sessionManager)

    val homeViewModel = HomeViewmodel(sessionService)
    val talkieViewModel = TalkieViewModel(sessionService, packetHandler)

}