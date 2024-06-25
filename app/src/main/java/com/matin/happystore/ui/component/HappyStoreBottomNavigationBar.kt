package com.matin.happystore.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.matin.happystore.navigation.TopLevelDestination

@Composable
fun HappyStoreBottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigationToDestination: (TopLevelDestination) -> Unit,
    bottomNavItems: List<TopLevelDestination>,
    inCartProductsCount: Int,
) {
    NavigationBar(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        bottomNavItems.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                onClick = {
                    onNavigationToDestination(destination)
                },
                icon = {
                    when {
                        (inCartProductsCount > 0) && destination == TopLevelDestination.Cart -> {
                            BadgedBox(badge = {
                                Badge {
                                    Text(text = "$inCartProductsCount")
                                }
                            }) {
                                Icon(imageVector = destination.icon, contentDescription = null)
                            }
                        }

                        else -> Icon(imageVector = destination.icon, contentDescription = null)
                    }
                },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any { it.route == destination.route } ?: false
}