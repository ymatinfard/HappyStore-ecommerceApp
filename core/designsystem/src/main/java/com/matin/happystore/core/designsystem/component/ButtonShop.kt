package com.matin.happystore.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons

@Composable
fun ButtonShop(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isBadgeVisible: Boolean = false,
) {
    Box(contentAlignment = Alignment.CenterStart) {
        Button(
            modifier = modifier.padding(start = 8.dp),
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(
                imageVector = HappyStoreIcons.ShoppingCart,
                contentDescription = "shopping",
            )
        }
        AnimatedVisibility(visible = isBadgeVisible, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                imageVector = HappyStoreIcons.Check,
                modifier =
                Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(color = Color.White),
                tint = MaterialTheme.colorScheme.inversePrimary,
                contentDescription = null,
            )
        }
    }
}