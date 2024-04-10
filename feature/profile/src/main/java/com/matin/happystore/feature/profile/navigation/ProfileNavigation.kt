package com.matin.happystore.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val PROFILE_ROUTE = "product_route"

fun NavGraphBuilder.profileScreen() {
    return composable(PROFILE_ROUTE) {
        profileScreen()
    }
}