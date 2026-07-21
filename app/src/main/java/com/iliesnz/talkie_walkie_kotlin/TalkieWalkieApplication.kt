package com.iliesnz.talkie_walkie_kotlin

import android.app.Application
import com.iliesnz.talkie_walkie_kotlin.container.AppContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class TalkieWalkieApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppContainer(applicationScope)
    }
}