package com.matin.happystore.feature.map

import androidx.lifecycle.ViewModel
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
    @Inject
    constructor() : ViewModel() {
        val mapUiState = MutableStateFlow(MapScreenUiState())
    }

data class MapScreenUiState(
    val mapUiSettings: MapUiSettings = MapUiSettings(),
    val mapProperties: MapProperties = MapProperties(),
)
