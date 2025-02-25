package com.example.locationapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocationAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CurrentLocation { locationData ->
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = "Latitude: ${locationData.latitude}, Longitude: ${locationData.longitude}"
            )
        }
    }
}

@Composable
fun CurrentLocation(
    locationData: @Composable (LocationData) -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: LocationViewModel = viewModel()
    val location by viewModel.location.collectAsState()
    val permissionChecker = remember { PermissionChecker(context) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                permissionChecker.requestLocationUpdates(viewModel)
            }
        }
    )

    LaunchedEffect(Unit) {
        if (permissionChecker.hasLocationPermission()) {
            permissionChecker.requestLocationUpdates(viewModel)
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (location == null) {
            CircularProgressIndicator()
        } else {
            location?.let {
                locationData(LocationData(it.latitude, it.longitude))
            }
        }
    }
}







