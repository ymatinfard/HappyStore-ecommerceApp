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

enum class TopLevelDestination(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {
    Products(
        PRODUCTS_ROUTE,
        R.string.products,
        Icons.AutoMirrored.Filled.List
    ),
    Cart(
        CART_ROUTE,
        R.string.shopping_cart,
        Icons.Default.ShoppingCart
    ),
    Profile(
        PROFILE_ROUTE,
        R.string.profile,
        Icons.Default.Person
    )
}