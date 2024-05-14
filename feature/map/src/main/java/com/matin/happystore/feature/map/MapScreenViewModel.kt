package com.matin.happystore.feature.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.matin.happystore.feature.map.MapScreenViewModel.Companion.fakeStores
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor() : ViewModel() {
    private val _mapUiState = MutableStateFlow(MapScreenUiState())
    val mapUiState: StateFlow<MapScreenUiState> = _mapUiState

    fun setCurrentLocation(lat: Double, lng: Double) {
        _mapUiState.update { state ->
            state.copy(
                cameraPositionState = CameraPositionState(
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(lat, lng), MAP_ZOOM
                    )
                )
            )
        }
    }

    companion object {
        const val MAP_ZOOM = 14F
        val fakeStores = listOf(
            StoreLocations(
                id = 123,
                title = "Maddy Store",
                lat = 38.208944757256624,
                lng = 15.557370297498132
            ),
            StoreLocations(
                id = 124,
                title = "Ani Store",
                lat = 38.19346658305723,
                lng = 15.553883918360398
            )
        )
    }
}

data class MapScreenUiState(
    val mapUiSettings: MapUiSettings = MapUiSettings(),
    val mapProperties: MapProperties = MapProperties(),
    val storeLocations: List<StoreLocations> = fakeStores,
    val cameraPositionState: CameraPositionState = CameraPositionState(),
)
