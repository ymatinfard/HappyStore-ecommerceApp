package com.matin.products.navigation

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.products.ProductsScreen
import com.matin.products.ProductsViewModel

const val PRODUCTS_ROUTE = "products_route"
const val PRODUCT_DETAIL_ROUTE = "product_detail/{product_id}"

fun NavController.navigateToProducts(navOptions: NavOptions) = navigate(PRODUCTS_ROUTE, navOptions)
fun NavController.navigateToProductDetail(productId: Int) = navigate("product_detail/$productId")

fun NavGraphBuilder.productsScreen(
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    onImageClick: (Int) -> Unit,
) {
    return composable(PRODUCTS_ROUTE) {
        val viewModel = hiltViewModel<ProductsViewModel>()
        ProductsScreen(
            viewModel,
            onMapClick,
            onSearchClick,
            setBottomBarVisibility,
            windowAdaptiveInfo,
            onImageClick,
        )
    }
}