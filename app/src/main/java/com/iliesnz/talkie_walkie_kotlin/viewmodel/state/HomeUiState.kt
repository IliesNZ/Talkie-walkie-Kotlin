package com.iliesnz.talkie_walkie_kotlin.viewmodel.state

sealed interface HomeUiState {
    object Base: HomeUiState
    object Loading: HomeUiState
    data class Success(val message: String? = null): HomeUiState
    data class Error(val message: String? = null): HomeUiState
}