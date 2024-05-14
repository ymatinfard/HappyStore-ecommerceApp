package com.matin.happystore.feature.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    var selectedStoreId by remember {
        mutableIntStateOf(NO_STORE_SELECTED)
    }
    setBottomBarVisibility(BottomBarVisibility.HIDDEN)

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    getCurrentLocation(context) { lat, lng ->
                        viewModel.setCurrentLocation(lat, lng)
                    }
                }
            })

    LaunchedEffect(Unit) {
        handleLocationPermission(context, viewModel, requestPermissionLauncher)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapContent(uiState) {
            selectedStoreId = it
        }

        StoreDetail(selectedStoreId, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun StoreDetail(selectedStoreId: Int, modifier: Modifier) {
    AnimatedVisibility(
        visible = selectedStoreId != NO_STORE_SELECTED,
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = modifier
    ) {
        // TODO() fetch store photos and details from server
        StorePhotoList()
    }
}

@Composable
fun GoogleMapContent(uiState: MapScreenUiState, onMarkerClick: (Int) -> Unit) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = uiState.cameraPositionState,
        uiSettings = uiState.mapUiSettings,
        properties = uiState.mapProperties,
        onMapClick = { onMarkerClick(NO_STORE_SELECTED) }
    ) {
        uiState.storeLocations.forEach { location ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        location.lat,
                        location.lng
                    ),
                ),
                title = location.title,
                onClick = {
                    onMarkerClick(location.id)
                    false
                }
            )
        }
    }
}

@Composable
private fun StorePhotoList() {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        LazyRow(contentPadding = PaddingValues(6.dp)) {
            items(3) { index ->
                Image(
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .width(300.dp)
                        .padding(end = if (index == 2) 0.dp else 6.dp) // Add padding between items, not after the last one
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    painter = painterResource(id = if (index == 0) R.drawable.img_store else R.drawable.img_store2),
                    contentDescription = null
                )
            }
        }
    }
}

private fun handleLocationPermission(
    context: Context,
    viewModel: MapScreenViewModel,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    if (hasLocationPermission(context)) {
        getCurrentLocation(context) { lat, lng ->
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
fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
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

const val NO_STORE_SELECTED = -1

