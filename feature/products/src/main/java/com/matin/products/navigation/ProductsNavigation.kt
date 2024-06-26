package com.matin.products.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.products.ProductsScreen
import com.matin.products.ProductsViewModel

const val PRODUCTS_ROUTE = "products_route"

fun NavController.navigateToProducts(navOptions: NavOptions) = navigate(PRODUCTS_ROUTE, navOptions)

fun NavGraphBuilder.productsScreen(
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit,
) {
    return composable(PRODUCTS_ROUTE) {
        val viewModel = hiltViewModel<ProductsViewModel>()
        ProductsScreen(viewModel, onMapClick, onSearchClick, setBottomBarVisibility)
    }
}
