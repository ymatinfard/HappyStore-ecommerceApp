package com.matin.happystore.ui.redux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Store<T>(initialState: T) {

    private val  _state = MutableStateFlow(initialState)
    val state : StateFlow<T> = _state.asStateFlow()

    private val mutex = Mutex()

    suspend fun update(updateBlock: (T) -> T) = mutex.withLock {
        val newState = updateBlock(_state.value)
        _state.value = newState
    }

    suspend fun read(readBlock: (T) -> Unit) = mutex.withLock {
        readBlock(_state.value)
    }
}