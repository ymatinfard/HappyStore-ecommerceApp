package com.matin.happystore.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.matin.happystore.feature.cart.navigation.CART_ROUTE
import com.matin.happystore.feature.cart.navigation.navigateToCart
import com.matin.happystore.feature.profile.navigation.PROFILE_ROUTE
import com.matin.happystore.feature.profile.navigation.navigateToProfile
import com.matin.happystore.navigation.TopLevelDestination
import com.matin.products.navigation.PRODUCTS_ROUTE
import com.matin.products.navigation.navigateToProducts


@Composable
fun rememberHappyStoreAppState(
    navController: NavHostController = rememberNavController(),
): HappyStoreAppState {
    return remember(navController) { HappyStoreAppState(navController) }
}

@Stable
class HappyStoreAppState(val navController: NavHostController) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() = when (currentDestination?.route) {
            PRODUCTS_ROUTE -> TopLevelDestination.Products
            CART_ROUTE -> TopLevelDestination.Cart
            PROFILE_ROUTE -> TopLevelDestination.Profile
            else -> null
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {

        val topNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.Products -> navController.navigateToProducts(topNavOptions)
            TopLevelDestination.Cart -> navController.navigateToCart(topNavOptions)
            TopLevelDestination.Profile -> navController.navigateToProfile(topNavOptions)
        }
    }
}