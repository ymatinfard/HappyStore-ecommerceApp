package com.matin.happystore.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.matin.happystore.navigation.HappyStoreNavigation
import com.matin.happystore.ui.BottomNavigationScreens
import com.matin.products.HappyStoreViewModel

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
    val inCartItemsCount = viewModel.inCartItemsCount.collectAsState()

    Scaffold(
        bottomBar = {
            HappyStoreBottomNavigation(
                navController = navController,
                selectedTabIndex = tabIndex,
                bottomNavItems = bottomNavItems,
                inCartItemsCount.value,
            )
        }
    ) {
        HappyStoreNavigation(navController)
    }
}

@Composable
fun HappyStoreBottomNavigation(
    navController: NavController,
    selectedTabIndex: MutableState<Int>,
    bottomNavItems: List<BottomNavigationScreens>,
    inCartProductsCount: Int,
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                    navController.navigate(bottomNavItem.route)
                },
                icon = {
                    when {
                        (inCartProductsCount > 0) && bottomNavItem is BottomNavigationScreens.Cart -> {
                            BadgedBox(badge = {
                                Badge {
                                    Text(text = "$inCartProductsCount")
                                }
                            }) {
                                Icon(imageVector = bottomNavItem.icon, contentDescription = null)
                            }
                        }

                        else -> Icon(imageVector = bottomNavItem.icon, contentDescription = null)
                    }
                }
            )
        }
    }
}