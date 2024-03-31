package com.matin.happystore.domain.util

sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error<Nothing>(val exception: Throwable) : Result<Nothing>
}