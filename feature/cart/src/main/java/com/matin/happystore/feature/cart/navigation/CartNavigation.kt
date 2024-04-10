package com.matin.happystore.feature.cart.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.happystore.feature.cart.CartViewModel
import com.matin.happystore.ui.screen.CartScreen

const val CART_ROUTE = "cart_route"

fun NavGraphBuilder.cartScreen() {
    return composable(CART_ROUTE) {
        val viewModel = hiltViewModel<CartViewModel>()
        CartScreen(viewModel = viewModel)
    }
}