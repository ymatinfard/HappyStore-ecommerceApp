package com.matin.happystore.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PROFILE_ROUTE = "product_route"

fun NavController.navigateToProfile(navOptions: NavOptions) = navigate(PROFILE_ROUTE, navOptions)
fun NavGraphBuilder.profileScreen() {
    return composable(PROFILE_ROUTE) {
        profileScreen()
    }
}