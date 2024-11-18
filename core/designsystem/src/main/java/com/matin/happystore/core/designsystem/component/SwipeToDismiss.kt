package com.matin.happystore.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SwipeToDismiss(
    modifier: Modifier,
    background: @Composable (Float) -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    val isDismissed = dismissState.currentValue == SwipeToDismissBoxValue.EndToStart

    AnimatedVisibility(
        modifier = modifier,
        visible = !isDismissed,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { background(dismissState.progress) }
        ) {
            content()
        }
    }
}

@Composable
fun SwipeToDismissBackground(progress: Float) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ) {

        val padding: Dp by animateDpAsState(
            if (progress < 0.5f) 4.dp else 0.dp, label = "padding"
        )

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(progress)
        ) {
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .height(maxWidth)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(percent = ((1 - progress) * 100).roundToInt()),
                color = MaterialTheme.colorScheme.error
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    if (progress in 0.1f..0.5f) {
                        val iconAlpha = (if (progress > 0.4f) 0.4 else 1).toFloat()

                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .graphicsLayer {
                                    alpha = iconAlpha
                                },
                            imageVector = Icons.Default.Delete,
                            tint = MaterialTheme.colorScheme.background,
                            contentDescription = "remove item"
                        )
                    }

                    val textAlpha = if (progress > 0.4f) 1f else 0.4f

                    if (progress > 0.4f) {
                        Text(
                            modifier = Modifier.graphicsLayer {
                                alpha = textAlpha
                            },
                            text = "Remove Item"
                        )
                    }
                }
            }
        }
    }
}
