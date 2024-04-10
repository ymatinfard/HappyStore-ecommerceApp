package com.matin.products.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.matin.products.ProductsViewModel
import com.matin.products.ProductsScreen

const val PRODUCTS_ROUTE = "products_route"

fun NavController.navigateToProducts(navOptions: NavOptions) = navigate(PRODUCTS_ROUTE, navOptions)

fun NavGraphBuilder.productsScreen() {
    return composable(PRODUCTS_ROUTE) {
        val viewModel = hiltViewModel<ProductsViewModel>()
        ProductsScreen(viewModel)
    }
}