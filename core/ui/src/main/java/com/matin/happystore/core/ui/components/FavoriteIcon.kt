package com.matin.happystore.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons

@Composable
fun FavoriteIcon(
    id: Int,
    isFavorite: Boolean,
    onFavoriteClick: (Int) -> Unit,
) {
    Image(
        imageVector = if (isFavorite) HappyStoreIcons.Favorites else HappyStoreIcons.FavoriteBorder,
        contentDescription = null,
        modifier =
        Modifier
            .clip(
                CircleShape,
            )
            .background(color = MaterialTheme.colorScheme.outline)
            .padding(3.dp)
            .clickable { onFavoriteClick(id) },
        colorFilter = ColorFilter.tint(if (isFavorite) Color.Red else Color.White),
    )
}