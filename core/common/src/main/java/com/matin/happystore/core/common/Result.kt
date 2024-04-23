package com.matin.happystore.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>

    data class Error<Nothing>(val exception: Throwable) : Result<Nothing>
}

fun <T> Flow<T>.asResource(): Flow<Result<T>> =
    map<T, Result<T>> {
        Result.Success(it)
    }.catch {
        emit(Result.Error(it))
    }
