package com.matin.happystore.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun Description(
    description: String,
    visible: Boolean,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter =
        slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } +
                expandVertically(
                    expandFrom = Alignment.Top,
                ) +
                fadeIn(
                    initialAlpha = 0.3f,
                ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        Text(text = description, modifier = Modifier.padding(6.dp))
    }
}