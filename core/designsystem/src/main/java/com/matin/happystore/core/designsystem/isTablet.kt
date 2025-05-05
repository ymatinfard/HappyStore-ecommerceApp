package com.matin.happystore.core.designsystem

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun isTablet(configuration: Configuration = LocalConfiguration.current): Boolean {
    val screenWidthDp = configuration.screenWidthDp.dp
    return screenWidthDp >= 600.dp
}