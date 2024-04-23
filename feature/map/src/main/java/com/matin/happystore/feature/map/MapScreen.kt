package com.matin.happystore.feature.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.maps.android.compose.GoogleMap
import com.matin.happystore.core.common.BottomBarVisibility

@Composable
fun MapScreen(
    viewModel: MapScreenViewModel,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit,
) {
    val uiState = viewModel.mapUiState.collectAsStateWithLifecycle()
    setBottomBarVisibility(BottomBarVisibility.HIDDEN)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = uiState.value.mapUiSettings,
        properties = uiState.value.mapProperties,
    )
}
