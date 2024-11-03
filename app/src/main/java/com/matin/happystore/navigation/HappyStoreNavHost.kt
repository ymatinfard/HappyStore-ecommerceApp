package com.matin.happystore.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.matin.happystore.feature.cart.navigation.cartScreen
import com.matin.happystore.feature.map.navigation.mapScreen
import com.matin.happystore.feature.map.navigation.navigateToMap
import com.matin.happystore.feature.profile.navigation.profileScreen
import com.matin.happystore.feature.search.navigation.navigateToSearch
import com.matin.happystore.feature.search.navigation.searchScreen
import com.matin.happystore.ui.HappyStoreAppState
import com.matin.products.DetailScreenRoute
import com.matin.products.DetailScreenViewModel
import com.matin.products.navigation.PRODUCTS_ROUTE
import com.matin.products.navigation.PRODUCT_DETAIL_ROUTE
import com.matin.products.navigation.navigateToProductDetail
import com.matin.products.navigation.productsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HappyStoreNavHost(appState: HappyStoreAppState, windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()) {
    SharedTransitionLayout {
        NavHost(navController = appState.navController, startDestination = PRODUCTS_ROUTE) {
            productsScreen(
                onMapClick = appState.navController::navigateToMap,
                onSearchClick = appState.navController::navigateToSearch,
                appState::setBottomBarVisibility,
                windowAdaptiveInfo = windowAdaptiveInfo,
                onImageClick = appState.navController::navigateToProductDetail
            )
            cartScreen()
            profileScreen()
            mapScreen(appState::setBottomBarVisibility)
            searchScreen(appState::setBottomBarVisibility, appState.navController::popBackStack)
            composable(
                route = PRODUCT_DETAIL_ROUTE,
                arguments = listOf(navArgument("product_id") { type = NavType.IntType }),

                ) { backStack ->
                val viewModel = hiltViewModel<DetailScreenViewModel>()
                val productId = backStack.arguments?.getInt("product_id") ?: 0
                DetailScreenRoute(
                    viewModel = viewModel,
                    productId = productId,
                    onBackClick = appState.navController::popBackStack,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}
