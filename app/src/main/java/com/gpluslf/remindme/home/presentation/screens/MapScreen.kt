package com.gpluslf.remindme.home.presentation.screens

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpluslf.remindme.R
import com.gpluslf.remindme.core.domain.Coordinates
import com.gpluslf.remindme.core.presentation.model.LocationService
import com.gpluslf.remindme.core.utils.permissions.PermissionStatus
import com.gpluslf.remindme.core.utils.permissions.rememberPermission
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import com.utsman.osmandcompose.rememberOverlayManagerState
import org.koin.compose.getKoin
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.CopyrightOverlay

fun Coordinates.toGeoPoint() = GeoPoint(latitude, longitude)
fun GeoPoint.toCoordinates() = Coordinates(latitude, longitude)

@Composable
fun MapScreen(
    onFloatingActionButtonClick: (Coordinates) -> Unit,
) {
    val locationService = getKoin().get<LocationService>()

    val locationPermission = rememberPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) { status ->
        if (status == PermissionStatus.Granted) {
            locationService.requestCurrentLocation()
        }
    }

    fun requestLocation() {
        if (locationPermission.status.isGranted) {
            locationService.requestCurrentLocation()
        } else {
            locationPermission.launchPermissionRequest()
        }
    }

    LaunchedEffect(Unit) {
        requestLocation()
    }

    LaunchedEffect(locationService.isLocationEnabled) {
        // TODO actions.setShowLocationDisabledAlert(locationService.isLocationEnabled == false)
    }

    val context = LocalContext.current


    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(0.0, 0.0)
        zoom = 12.0
    }

    val markerState = rememberMarkerState(
        geoPoint = GeoPoint(0.0, 0.0)
    )

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    val overlayManagerState = rememberOverlayManagerState()

    LaunchedEffect(locationService.coordinates) {
        val coordinates = locationService.coordinates ?: return@LaunchedEffect
        val point = coordinates.toGeoPoint()
        markerState.geoPoint = point
        cameraState.geoPoint = point
    }

    SideEffect {
        mapProperties = mapProperties
            .copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.DEFAULT_TILE_SOURCE)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFloatingActionButtonClick(markerState.geoPoint.toCoordinates()) },
            ) {
                Icon(Icons.Outlined.Save, "Save Position")
            }
        }
    ) { padding ->
        OpenStreetMap(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            cameraState = cameraState,
            properties = mapProperties,
            onMapClick = {
                markerState.geoPoint = it
            },
            overlayManagerState = overlayManagerState,
            onFirstLoadListener = {
                val copyright = CopyrightOverlay(context)
                overlayManagerState.overlayManager.add(copyright)
            }
        ) {
            Marker(
                state = markerState,
                icon = context.getDrawable(R.drawable.pin),
                title = "Depok",
                snippet = "Jawa barat"
            ) {
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(7.dp)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it.title)
                    Text(text = it.snippet, fontSize = 10.sp)
                }
            }
        }
    }
}
