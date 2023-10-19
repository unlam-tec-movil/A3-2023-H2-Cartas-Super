package ar.edu.unlam.mobile.scaffold.ui.screens.map.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ar.edu.unlam.mobile.scaffold.ui.screens.map.LocationUtils

@Composable
fun LocationPermissionsAndSettingDialogs(
    updateCurrentLocation: () -> Unit,
) {
    var requestLocationSetting by remember { mutableStateOf(false) }

    if(LocationUtils.isLocationPermissionGranted(LocalContext.current)) {
        SideEffect {
            requestLocationSetting = true

        }
    } else {
        LocationPermissionsDialog(
            onPermissionGranted = {
                requestLocationSetting = true
            },
            onPermissionDenied = {
                requestLocationSetting = true
            },
        )
    }

    if (requestLocationSetting) {

        LocationSettingDialog(
            onSuccess = {
                requestLocationSetting = false
                updateCurrentLocation()
            },
            onFailure = {
                requestLocationSetting = false
                updateCurrentLocation()
            },
        )
    }
}