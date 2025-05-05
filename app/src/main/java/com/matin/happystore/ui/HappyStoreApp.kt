package com.matin.happystore.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.core.layout.WindowWidthSizeClass
import com.matin.happystore.R
import com.matin.happystore.core.common.Constants
import com.matin.happystore.core.designsystem.LocalAnimatedVisibilityScope
import com.matin.happystore.core.designsystem.LocalSharedTransitionScope
import com.matin.happystore.feature.cart.navigation.cartScreen
import com.matin.happystore.feature.map.navigation.mapScreen
import com.matin.happystore.feature.map.navigation.navigateToMap
import com.matin.happystore.feature.profile.navigation.profileScreen
import com.matin.happystore.feature.search.navigation.navigateToSearch
import com.matin.happystore.feature.search.navigation.searchScreen
import com.matin.happystore.navigation.TopLevelDestination
import com.matin.products.DetailScreenRoute
import com.matin.products.DetailScreenViewModel
import com.matin.products.navigation.PRODUCTS_ROUTE
import com.matin.products.navigation.productsScreen
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HappyStoreApp(
    appState: HappyStoreAppState,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            NavHost(navController = navController, startDestination = MainDestination.HOME_ROUTE) {
                composableWithLocalComposition(MainDestination.HOME_ROUTE) {
                    MainContainer(appState, windowAdaptiveInfo,
                        onItemSelected = { navController.navigate("${MainDestination.DETAIL_ROUTE}/$it") })
                }
                composableWithLocalComposition(
                    route = "${MainDestination.DETAIL_ROUTE}/{${Constants.PRODUCT_ID}}",
                    arguments = listOf(navArgument(Constants.PRODUCT_ID) { type = NavType.IntType }),
                ) {
                    val viewModel = hiltViewModel<DetailScreenViewModel>()
                    DetailScreenRoute(
                        viewModel = viewModel,
                        onBackClick = navController::popBackStack,
                    )
                }
            }
        }
    }
}

@Composable
private fun MainContainer(
    appState: HappyStoreAppState,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    onItemSelected: (id: Int) -> Unit,
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
            MainContent(snackbarHostState, appState, onItemSelected)
        }
    } else {
        MainContent(snackbarHostState, appState, onItemSelected)
    }
}

@Composable
fun MainContent(
    snackbarHostState: SnackbarHostState,
    appState: HappyStoreAppState,
    onItemSelected: (id: Int) -> Unit
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
            NavHost(navController = appState.navController, startDestination = PRODUCTS_ROUTE) {
                addMainGraph(appState, onItemSelected)
            }
        }
    }
}

private fun NavGraphBuilder.addMainGraph(appState: HappyStoreAppState, onItemSelected: (id: Int) -> Unit) {
    productsScreen(
        onMapClick = { appState.navController.navigateToMap() },
        onSearchClick = { appState.navController.navigateToSearch() },
        setBottomBarVisibility = appState::setBottomBarVisibility,
        onImageClick = onItemSelected,
    )
    cartScreen(onItemSelected = onItemSelected)
    profileScreen()
    mapScreen(appState::setBottomBarVisibility)
    searchScreen(appState::setBottomBarVisibility, appState.navController::popBackStack)
}

private fun navigateToDetailScreen(appState: HappyStoreAppState, it: Int) {
    appState.navController.navigate("${MainDestination.DETAIL_ROUTE}/$it")
}

@Composable
fun layoutType(windowAdaptiveInfo: WindowAdaptiveInfo) =
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

fun NavGraphBuilder.composableWithLocalComposition(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = route, arguments = arguments) {
        CompositionLocalProvider(
            LocalAnimatedVisibilityScope provides this@composable,
        ) {
            content(it)
        }
    }
}

object MainDestination {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
}