package com.matin.happystore.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.matin.happystore.R
import com.matin.happystore.navigation.HappyStoreNavHost
import com.matin.happystore.navigation.TopLevelDestination
import com.matin.happystore.ui.component.HappyStoreBottomNavigationBar
import kotlinx.coroutines.flow.map

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HappyStoreApp(appState: HappyStoreAppState) {
    val viewModel = hiltViewModel<MainActivityViewModel>()
    val inCartItemsCount =
        viewModel.mainScreenUiState.map { it.inCartProductsCount }.collectAsState(initial = 0).value
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsState()
    val bottomBarVisibility = appState.bottomBarVisibility.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.no_internet_connection),
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomBarVisibility.isVisible()) {
                HappyStoreBottomNavigationBar(
                    currentDestination = appState.currentDestination,
                    onNavigationToDestination = appState::navigateToTopLevelDestination,
                    bottomNavItems = TopLevelDestination.entries,
                    inCartItemsCount,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        HappyStoreNavHost(appState)
    }
}
