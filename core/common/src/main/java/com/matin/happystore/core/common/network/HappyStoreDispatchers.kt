package com.matin.happystore.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val happyStoreDispatchers: HappyStoreDispatchers)

enum class HappyStoreDispatchers {
    Default,
    IO,
}
