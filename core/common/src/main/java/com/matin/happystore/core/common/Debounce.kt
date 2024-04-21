package com.matin.happystore.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    waitTime: Long,
    scope: CoroutineScope,
    destinationFunc: (T) -> Unit,
): (T) -> Unit {
    var job: Job? = null
    return { param: T ->
        job?.cancel()
        job =
            scope.launch {
                delay(waitTime)
                destinationFunc(param)
            }
    }
}
