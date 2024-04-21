package com.matin.happystore.core.common

sealed interface DataLoadingState {
    data object Loading : DataLoadingState

    data object Loaded : DataLoadingState

    data class Error(val error: Throwable) : DataLoadingState
}
