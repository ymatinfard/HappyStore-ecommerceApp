package com.matin.happystore.feature.map.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.feature.map.MapScreen
import com.matin.happystore.feature.map.MapScreenViewModel

const val MAP_ROUTE = "map_route"

fun NavController.navigateToMap() = navigate(MAP_ROUTE)

fun NavGraphBuilder.mapScreen(setBottomBarVisibility: (BottomBarVisibility) -> Unit) {
    return composable(MAP_ROUTE) {
        val viewModel = hiltViewModel<MapScreenViewModel>()
        MapScreen(viewModel = viewModel, setBottomBarVisibility)
    }
}
