package com.matin.happystore.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.matin.happystore.navigation.HappyStoreNavigation
import com.matin.happystore.ui.BottomNavigationScreens
import com.matin.happystore.ui.HappyStoreViewModel
import com.matin.happystore.widgets.HappyStoreBottomNavigation
import kotlinx.coroutines.flow.map

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val tabIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val bottomNavItems = listOf(
        BottomNavigationScreens.Products,
        BottomNavigationScreens.Cart,
        BottomNavigationScreens.Profile,
    )

    val viewModel = hiltViewModel<HappyStoreViewModel>()
    val inCartProductsState = viewModel.store.state.map { it.inCartProductIds }.collectAsState(initial = emptySet())

    Scaffold(
        bottomBar = {
            HappyStoreBottomNavigation(
                navController = navController,
                selectedTabIndex = tabIndex,
                bottomNavItems = bottomNavItems,
                inCartProductsState.value.size,
            )
        }
    ) {
        HappyStoreNavigation(navController)
    }
}