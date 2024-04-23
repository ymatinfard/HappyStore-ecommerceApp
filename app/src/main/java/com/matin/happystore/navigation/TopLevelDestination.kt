package com.matin.happystore.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.matin.happystore.R
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
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
        HappyStoreIcons.List,
    ),
    Cart(
        CART_ROUTE,
        R.string.shopping_cart,
        HappyStoreIcons.ShoppingCart,
    ),
    Profile(
        PROFILE_ROUTE,
        R.string.profile,
        HappyStoreIcons.Profile,
    ),
}
