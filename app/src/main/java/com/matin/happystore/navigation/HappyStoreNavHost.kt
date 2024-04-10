package com.matin.happystore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.matin.happystore.feature.cart.navigation.cartScreen
import com.matin.happystore.feature.profile.navigation.profileScreen
import com.matin.products.navigation.PRODUCTS_ROUTE
import com.matin.products.navigation.productsScreen

@Composable
fun HappyStoreNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = PRODUCTS_ROUTE
    ) {
        productsScreen()
        cartScreen()
        profileScreen()
    }
}