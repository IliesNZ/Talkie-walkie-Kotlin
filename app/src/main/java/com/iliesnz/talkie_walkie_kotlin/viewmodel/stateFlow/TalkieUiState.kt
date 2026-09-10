package com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow

import android.os.Message

sealed interface TalkieUiState {
    object base: TalkieUiState
    object incomingSound: TalkieUiState
    object comingOutSound: TalkieUiState
    data class Error(val message: String? = null): TalkieUiState
}