package com.matin.happystore.core.common

import app.cash.turbine.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ResultTest {

    @Test
    fun asResourceCatchException() = runTest {
        flow {
            emit(123)
            throw Exception("error")
        }.asResource().test {
            assertEquals(Result.Success(123), awaitItem())
            assertEquals("error", (awaitItem() as Result.Error).exception.message)

            awaitComplete()
        }
    }
}