package com.matin.happystore.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.window.core.layout.WindowWidthSizeClass
import com.matin.happystore.R
import com.matin.happystore.navigation.HappyStoreNavHost
import com.matin.happystore.navigation.TopLevelDestination
import kotlinx.coroutines.flow.map

@Composable
fun HappyStoreApp(
    appState: HappyStoreAppState,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val viewModel = hiltViewModel<MainActivityViewModel>()
    val inCartItemsCount =
        viewModel.mainScreenUiState.map { it.inCartProductsCount }.collectAsState(initial = 0).value
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsState()
    val bottomBarVisibility = appState.bottomBarVisibility.collectAsState().value
    val context = LocalContext.current
    val currentDestination = appState.currentDestination

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.no_internet_connection),
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    if (bottomBarVisibility.isVisible()) {
        NavigationSuiteScaffold(
            navigationSuiteItems =
            {
                TopLevelDestination.entries.forEach { destination ->
                    val isSelected = currentDestination.isTopLevelDestinationInHierarchy(
                        destination
                    )
                    item(
                        icon = {
                            BadgedIcon(
                                destination = destination,
                                inCartItemsCount = inCartItemsCount
                            )
                        },
                        label = { Text(stringResource(destination.resourceId)) },
                        selected = isSelected,
                        onClick = { appState.navigateToTopLevelDestination(destination) }
                    )
                }

            },
            layoutType = layoutType(windowAdaptiveInfo)
        ) {
            MainContent(snackbarHostState, appState)
        }
    } else {
        MainContent(snackbarHostState, appState)
    }
}

@Composable
private fun MainContent(
    snackbarHostState: SnackbarHostState,
    appState: HappyStoreAppState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            HappyStoreNavHost(appState)
        }
    }
}

@Composable
private fun layoutType(windowAdaptiveInfo: WindowAdaptiveInfo) =
    if (windowAdaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
        NavigationSuiteType.NavigationBar
    else
        NavigationSuiteType.NavigationRail

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any { it.route == destination.route } ?: false
}

@Composable
fun BadgedIcon(
    destination: TopLevelDestination,
    inCartItemsCount: Int
) {
    if (destination == TopLevelDestination.Cart && inCartItemsCount > 0) {
        BadgedBox(badge = {
            Badge { Text(text = "$inCartItemsCount") }
        }) {
            Icon(
                imageVector = destination.icon,
                contentDescription = null
            )
        }
    } else {
        Icon(
            imageVector = destination.icon,
            contentDescription = null
        )
    }
}