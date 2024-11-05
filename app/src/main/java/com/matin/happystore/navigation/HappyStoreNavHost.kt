@file:OptIn(
    ExperimentalSharedTransitionApi::class
)

package com.matin.happystore.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.matin.happystore.core.ui.LocalAnimatedVisibilityScope
import com.matin.happystore.core.ui.LocalSharedTransitionScope
import com.matin.happystore.feature.cart.navigation.cartScreen
import com.matin.happystore.feature.map.navigation.mapScreen
import com.matin.happystore.feature.map.navigation.navigateToMap
import com.matin.happystore.feature.profile.navigation.profileScreen
import com.matin.happystore.feature.search.navigation.navigateToSearch
import com.matin.happystore.feature.search.navigation.searchScreen
import com.matin.happystore.ui.HappyStoreAppState
import com.matin.products.DetailScreenRoute
import com.matin.products.DetailScreenViewModel
import com.matin.products.ProductsScreen
import com.matin.products.ProductsViewModel
import com.matin.products.navigation.PRODUCTS_ROUTE
import com.matin.products.navigation.PRODUCT_DETAIL_ROUTE
import com.matin.products.navigation.navigateToProductDetail


@Composable
fun HappyStoreNavHost(
    appState: HappyStoreAppState,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            NavHost(navController = appState.navController, startDestination = PRODUCTS_ROUTE) {
                cartScreen()
                profileScreen()
                mapScreen(appState::setBottomBarVisibility)
                searchScreen(appState::setBottomBarVisibility, appState.navController::popBackStack)
                composableWithLocalComposition(PRODUCTS_ROUTE) {
                    val viewModel = hiltViewModel<ProductsViewModel>()
                    ProductsScreen(
                        viewModel = viewModel,
                        onMapClick = appState.navController::navigateToMap,
                        onSearchClick = appState.navController::navigateToSearch,
                        setBottomBarVisibility = appState::setBottomBarVisibility,
                        windowAdaptiveInfo = windowAdaptiveInfo,
                        onImageClick = appState.navController::navigateToProductDetail,
                    )
                }
                composableWithLocalComposition(
                    route = PRODUCT_DETAIL_ROUTE,
                    arguments = listOf(navArgument("product_id") { type = NavType.IntType }),
                ) { backStack ->
                    val viewModel = hiltViewModel<DetailScreenViewModel>()
                    val productId = backStack.arguments?.getInt("product_id") ?: 0
                    DetailScreenRoute(
                        viewModel = viewModel,
                        productId = productId,
                        onBackClick = appState.navController::popBackStack,
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.composableWithLocalComposition(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = route, arguments = arguments) {
        CompositionLocalProvider(
            LocalAnimatedVisibilityScope provides this@composable,
        ) {
            content(it)
        }
    }
}