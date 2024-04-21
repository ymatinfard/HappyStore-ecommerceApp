package com.matin.happystore.core.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

fun String.clipIfLengthy() = if (this.length > 20) this.take(20).plus("...") else this

fun Modifier.shimmerEffect(): Modifier =
    composed {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }

        val transition = rememberInfiniteTransition()

        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 1000),
                ),
            label = "",
        )

        background(
            brush =
                Brush.linearGradient(
                    colors =
                        listOf(
                            Color(0x0FE6E2E2),
                            Color(0x0F9C9999),
                            Color(0x0F484753),
                        ),
                    start = Offset(startOffsetX, 0f),
                    end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
                ),
        ).onGloballyPositioned {
            size = it.size
        }
    }
