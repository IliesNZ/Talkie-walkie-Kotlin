package com.iliesnz.talkie_walkie_kotlin.container

import com.iliesnz.talkie_walkie_kotlin.viewmodel.HomeViewmodel
import com.iliesnz.talkie_walkie_kotlin.service.interfaces.IHomeService
import com.iliesnz.talkie_walkie_kotlin.service.HomeService
import com.iliesnz.talkie_walkie_kotlin.repository.interfaces.IHomeRepository
import com.iliesnz.talkie_walkie_kotlin.repository.HomeRepository

object AppContainer {

    private val homeRepository: IHomeRepository = HomeRepository()

    private val homeService: IHomeService = HomeService(homeRepository)

    val homeViewModel = HomeViewmodel(homeService)


}