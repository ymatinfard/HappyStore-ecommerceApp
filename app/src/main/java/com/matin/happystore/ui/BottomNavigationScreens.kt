package com.matin.happystore.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.matin.happystore.R
import com.matin.happystore.navigation.HappyStoreScreens

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {
    data object Home : BottomNavigationScreens(
        HappyStoreScreens.HomeScreen.name,
        R.string.home,
        Icons.Default.Home
    )

    data object Products : BottomNavigationScreens(
        HappyStoreScreens.ProductsScreen.name, R.string.products,
        Icons.AutoMirrored.Filled.List
    )
}