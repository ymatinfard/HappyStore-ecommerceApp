package com.matin.happystore.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.matin.happystore.ui.CartScreen
import com.matin.happystore.ui.HappyStoreViewModel
import com.matin.happystore.ui.ProfileScreen
import com.matin.happystore.ui.ProductsScreen

@Composable
fun HappyStoreNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = HappyStoreScreens.ProductsScreen.name
    ) {

        composable(HappyStoreScreens.Profile.name) {
            ProfileScreen()
        }

        composable(HappyStoreScreens.ProductsScreen.name) {
            val viewModel = hiltViewModel<HappyStoreViewModel>()
            ProductsScreen(viewModel)
        }

        composable(HappyStoreScreens.CartScreen.name) {
            val viewModel = hiltViewModel<HappyStoreViewModel>()
            CartScreen(viewModel)
        }
    }
}