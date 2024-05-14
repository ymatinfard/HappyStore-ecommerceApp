package com.matin.happystore.feature.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.matin.happystore.core.common.BottomBarVisibility

@Composable
fun MapScreen(
    viewModel: MapScreenViewModel,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.mapUiState.collectAsStateWithLifecycle()
    setBottomBarVisibility(BottomBarVisibility.HIDDEN)

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    getCurrentLocation2(context) { lat, lng ->
                        viewModel.setCurrentLocation(lat, lng)
                    }
                }
            })

    LaunchedEffect(Unit) {
        handleLocationPermission(context, viewModel, requestPermissionLauncher)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = uiState.cameraPositionState,
        uiSettings = uiState.mapUiSettings,
        properties = uiState.mapProperties,
    ) {
        uiState.storeLocations.forEach { location ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        location.lat,
                        location.lng
                    ),
                ),
                title = location.title
            )
        }
    }
}


private fun handleLocationPermission(
    context: Context,
    viewModel: MapScreenViewModel,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    if (hasLocationPermission(context)) {
        getCurrentLocation2(context) { lat, lng ->
            viewModel.setCurrentLocation(lat, lng)
        }
    } else {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
fun getCurrentLocation2(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val lat = location.latitude
            val long = location.longitude
            callback(lat, long)
        }
    }
        .addOnFailureListener { exception ->
            exception.printStackTrace()
        }
}

