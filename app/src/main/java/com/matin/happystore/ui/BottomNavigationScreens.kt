package com.matin.happystore.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.matin.happystore.R
import com.matin.happystore.navigation.HappyStoreScreens

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {

    data object Products : BottomNavigationScreens(
        HappyStoreScreens.ProductsScreen.name,
        R.string.products,
        Icons.AutoMirrored.Filled.List
    )

    data object ShoppingCart : BottomNavigationScreens(
        HappyStoreScreens.ShoppingCartScreen.name,
        R.string.shopping_cart,
        Icons.Default.ShoppingCart
    )

    data object Profile : BottomNavigationScreens(
        HappyStoreScreens.Profile.name,
        R.string.profile,
        Icons.Default.Person
    )
}