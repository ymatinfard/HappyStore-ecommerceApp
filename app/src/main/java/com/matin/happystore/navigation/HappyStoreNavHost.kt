package com.matin.happystore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.matin.happystore.feature.cart.navigation.cartScreen
import com.matin.happystore.feature.map.navigation.mapScreen
import com.matin.happystore.feature.map.navigation.navigateToMap
import com.matin.happystore.feature.profile.navigation.profileScreen
import com.matin.happystore.ui.HappyStoreAppState
import com.matin.products.navigation.PRODUCTS_ROUTE
import com.matin.products.navigation.productsScreen

@Composable
fun HappyStoreNavHost(appState: HappyStoreAppState) {
    NavHost(
        navController = appState.navController,
        startDestination = PRODUCTS_ROUTE,
    ) {
        productsScreen(
            onMapClick = appState.navController::navigateToMap,
            appState::setBottomBarVisibility,
        )
        cartScreen()
        profileScreen()
        mapScreen(appState::setBottomBarVisibility)
    }
}
