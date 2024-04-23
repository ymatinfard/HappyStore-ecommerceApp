package com.matin.happystore.feature.cart.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.matin.happystore.feature.cart.CartScreen
import com.matin.happystore.feature.cart.CartViewModel

const val CART_ROUTE = "cart_route"

fun NavController.navigateToCart(navOptions: NavOptions) = navigate(CART_ROUTE, navOptions)

fun NavGraphBuilder.cartScreen() {
    return composable(CART_ROUTE) {
        val viewModel = hiltViewModel<CartViewModel>()
        CartScreen(viewModel = viewModel)
    }
}
