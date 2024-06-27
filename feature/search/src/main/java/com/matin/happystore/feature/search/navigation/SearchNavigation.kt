package com.matin.happystore.feature.search.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.feature.search.BackClickListener
import com.matin.happystore.feature.search.SearchScreen
import com.matin.happystore.feature.search.SearchViewModel

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchScreen(setBottomBarVisibility: (BottomBarVisibility) -> Unit, onBackClick: BackClickListener) {
    return composable(SEARCH_ROUTE) {
        val viewModel = hiltViewModel<SearchViewModel>()
        SearchScreen(viewModel = viewModel, setBottomBarVisibility, onBackClick)
    }
}