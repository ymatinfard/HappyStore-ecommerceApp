package com.matin.happystore.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.matin.happystore.navigation.HappyStoreNavigation
import com.matin.happystore.widgets.HappyStoreBottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val tabIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(
        bottomBar = { HappyStoreBottomNavigation(navController = navController, selectedTabIndex = tabIndex) }
    ) {
        HappyStoreNavigation(navController)
    }
}