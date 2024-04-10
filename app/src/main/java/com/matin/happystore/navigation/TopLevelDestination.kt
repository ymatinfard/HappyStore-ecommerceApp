package com.matin.happystore.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.matin.happystore.R
import com.matin.happystore.feature.cart.navigation.CART_ROUTE
import com.matin.happystore.feature.profile.navigation.PROFILE_ROUTE
import com.matin.products.navigation.PRODUCTS_ROUTE

sealed class TopLevelDestination(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {

    data object Products : TopLevelDestination(
        PRODUCTS_ROUTE,
        R.string.products,
        Icons.AutoMirrored.Filled.List
    )

    data object Cart : TopLevelDestination(
        CART_ROUTE,
        R.string.shopping_cart,
        Icons.Default.ShoppingCart
    )

    data object Profile : TopLevelDestination(
        PROFILE_ROUTE,
        R.string.profile,
        Icons.Default.Person
    )
}